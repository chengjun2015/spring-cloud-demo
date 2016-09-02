package com.gavin.controller;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.domain.order.Item;
import com.gavin.domain.product.Product;
import com.gavin.exception.product.ProductException;
import com.gavin.model.request.product.CancelReservationReqModel;
import com.gavin.model.request.product.CreateProductReqModel;
import com.gavin.model.request.product.ReserveProductsReqModel;
import com.gavin.model.response.Response;
import com.gavin.model.response.product.ProductDetailModel;
import com.gavin.model.response.product.ReserveProductsResModel;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<Product> createProduct(@Valid @RequestBody CreateProductReqModel model) {
        Product product = new Product();
        product.setTitle(model.getTitle());
        product.setCategoryId(model.getCategoryId());
        product.setPrice(model.getPrice());
        product.setStock(model.getStock());
        product.setComment(model.getComment());

        productService.createProduct(product);
        logger.info("商品" + model.getTitle() + "录入成功。");

        Response<Product> response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
        response.setData(product);
        return response;
    }

    @RequestMapping(value = "/{product_id}", method = RequestMethod.GET)
    public Product searchProductById(@PathVariable("product_id") Long productId) {
        return productService.searchProductById(productId);
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response<ReserveProductsResModel> reserveProducts(@Valid @RequestBody ReserveProductsReqModel model) {
        Response<ReserveProductsResModel> response;
        try {
            Item[] items = model.getItems();
            List<ProductDetailModel> productDetails = productService.reserve(items);
            for (ProductDetailModel itemDetail : productDetails) {
                logger.info("商品" + itemDetail.getProductId() + "已保留" + itemDetail.getQuantity() + "件。");
            }

            ReserveProductsResModel reserveResModel = new ReserveProductsResModel();
            reserveResModel.setProductDetails(productDetails);

            response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
            response.setMessage("订单中订购的商品已保留成功。");
            response.setData(reserveResModel);
        } catch (ProductException exception) {
            logger.warn(exception.getMessage());
            response = new Response(ResponseCodeConsts.CODE_PRODUCT_INSUFFICIENT_STOCK);
            response.setMessage(exception.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response cancelReservation(@Valid @RequestBody CancelReservationReqModel model) {
        Response response;
        Item[] items = model.getItems();

        productService.cancel(items);
        logger.info("为订单" + model.getOrderId() + "的库存保留已取消。");
        response = new Response(ResponseCodeConsts.CODE_PRODUCT_NORMAL);
        response.setMessage("为订单" + model.getOrderId() + "的库存保留已取消。");
        return response;
    }

}
