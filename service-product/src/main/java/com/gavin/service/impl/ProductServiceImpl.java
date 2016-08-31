package com.gavin.service.impl;

import com.gavin.dao.PointRewardPlanDao;
import com.gavin.dao.ProductDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.product.PointRewardPlan;
import com.gavin.domain.product.Product;
import com.gavin.exception.product.ProductException;
import com.gavin.model.response.product.ProductDetailModel;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductDao productDao;

    @Resource
    private PointRewardPlanDao pointRewardPlanDao;

    @Override
    @Transactional
    public void createProduct(Product product) {
        productDao.create(product);
    }

    @Override
    public Product searchProductById(Long productId) {
        return productDao.searchById(productId);
    }

    @Override
    @Transactional
    public List<ProductDetailModel> reserve(Item[] items) {
        Long[] productIds = new Long[items.length];
        for (int i = 0; i < items.length; i++) {
            productIds[i] = items[i].getProductId();
        }

        List<Product> products = productDao.searchByIds(productIds);

        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        List<ProductDetailModel> productDetails = new ArrayList<>();

        for (Item item : items) {
            Long productId = item.getProductId();
            Product product = productMap.get(productId);

            // 订单中此商品的订购数超过库存。
            if (item.getQuantity() > product.getStock()) {
                logger.info("订单中商品: " + product.getTitle() + "的订购数: " + item.getQuantity() + ",库存数: " + product.getStock() + "。");
                throw new ProductException(product.getTitle() + "库存不足。");
            }

            // 从该商品的库存中冻结与订单相应的数目。
            productDao.decreaseStock(item.getProductId(), item.getQuantity());

            ProductDetailModel itemDetail = new ProductDetailModel();

            BeanUtils.copyProperties(item, itemDetail);

            itemDetail.setPrice(product.getPrice());

            // 查找该商品是否有处于有效期内的返点计划。
            PointRewardPlan pointRewardPlan = pointRewardPlanDao.searchApplicativeByProductId(productId);
            if (null != pointRewardPlan) {
                itemDetail.setRatio(pointRewardPlan.getRatio());
            }

            productDetails.add(itemDetail);
        }

        return productDetails;
    }

    @Override
    @Transactional
    public void restore(Item[] items) {
        for (Item item : items) {
            productDao.increaseStock(item.getProductId(), item.getQuantity());
        }
    }

}
