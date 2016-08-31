package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.product.ProductException;
import com.gavin.model.request.product.ReserveProductsReqModel;
import com.gavin.model.request.product.CancelReservationReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.product.ProductDetailModel;
import com.gavin.model.response.product.ReserveProductResModel;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Response<Product> createProduct(@RequestParam(value = "title") String title,
                                           @RequestParam(value = "category_id") Long categoryId,
                                           @RequestParam(value = "price") Float price,
                                           @RequestParam(value = "stock", required = false, defaultValue = "0") Integer stock,
                                           @RequestParam(value = "comment", required = false) String comment) {
        Product product = new Product();
        product.setTitle(title);
        product.setCategoryId(categoryId);
        product.setPrice(price);
        product.setStock(stock);
        product.setComment(comment);
        productService.createProduct(product);

        Response<Product> response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
        response.setData(product);
        return response;
    }

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    public Product searchProductById(@PathVariable("product_id") Long productId) {
        return productService.searchProductById(productId);
    }

    @RequestMapping(value = "/products/reserve", method = RequestMethod.PUT)
    public Response<ReserveProductResModel> reserveProducts(@RequestBody ReserveProductsReqModel model) {
        Response<ReserveProductResModel> response;
        try {
            Item[] items = model.getItems();
            List<ProductDetailModel> productDetails = productService.reserve(items);
            for (ProductDetailModel itemDetail : productDetails) {
                logger.info("订购的商品" + itemDetail.getProductId() + "已确保" + itemDetail.getQuantity() + "件在库。");
            }

            ReserveProductResModel reserveResModel = new ReserveProductResModel();
            reserveResModel.setProductDetails(productDetails);

            response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
            response.setData(reserveResModel);
        } catch (ProductException exception) {
            logger.warn(exception.getMessage());
            response = new Response(ResponseCodeConsts.CODE_PRODUCT_INSUFFICIENT_STOCK);
            response.setMessage(exception.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/products/cancel", method = RequestMethod.PUT)
    public Response cancelReservation(@RequestBody CancelReservationReqModel model) {
        Response response;
        Item[] items = model.getItems();

        try {
            productService.restore(items);
            logger.info("为订单" + model.getOrderId() + "而保留的库存数已取消。");
            response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
        } catch (Exception e) {
            response = new Response(ResponseCodeConsts.CODE_ORDER_RESTORE_FAILED);
        }
        return response;
    }

}
