package com.gavin.service.impl;

import com.gavin.dao.PointRewardPlanDao;
import com.gavin.dao.ProductDao;
import com.gavin.domain.order.Item;
import com.gavin.domain.product.PointRewardPlan;
import com.gavin.domain.product.Product;
import com.gavin.exception.order.OrderException;
import com.gavin.model.response.order.ItemDetailModel;
import com.gavin.model.response.order.OrderDetailModel;
import com.gavin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    public Long createProduct(Product product) {
        productDao.create(product);
        return product.getId();
    }

    @Override
    public Product searchProductById(Long productId) {
        return productDao.searchById(productId);
    }

    @Override
    @Transactional
    public OrderDetailModel reserve(Item[] items) {

        OrderDetailModel orderDetail = new OrderDetailModel();
        List<ItemDetailModel> itemDetails = new ArrayList<>();

        Long[] productIds = new Long[items.length];
        for (int i = 0; i < items.length; i++) {
            productIds[i] = items[i].getProductId();
        }

        List<Product> products = productDao.searchByIds(productIds);

        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        BigDecimal totalPricePerOrder = new BigDecimal("0");
        BigDecimal totalPointsPerOrder = new BigDecimal("0");

        for (Item item : items) {
            Long productId = item.getProductId();
            Product product = productMap.get(productId);

            // 订单中此商品的订购数超过库存
            if (item.getQuantity() > product.getStock()) {
                logger.info("订单中商品: " + product.getTitle() + "的订购数: " + item.getQuantity() + ",库存数: " + product.getStock() + "。");
                throw new OrderException(product.getTitle() + "库存不足");
            }

            // 从该商品的库存中冻结与订单相应的数目。
            productDao.decreaseStock(item.getProductId(), item.getQuantity());

            ItemDetailModel itemDetail = new ItemDetailModel();
            itemDetail.setItem(item);

            // 计算该商品的总金额。
            BigDecimal totalPricePerItem = new BigDecimal(product.getPrice() * item.getQuantity());
            itemDetail.setTotalPrice(totalPricePerItem);

            // 计算该商品可获得的积分数。
            BigDecimal totalPointsPerItem = new BigDecimal("0");
            PointRewardPlan pointRewardPlan = pointRewardPlanDao.searchApplicativeByProductId(productId);
            if (pointRewardPlan != null) {
                totalPointsPerItem = totalPricePerItem.multiply(new BigDecimal(pointRewardPlan.getRatio())).setScale(0, BigDecimal.ROUND_HALF_UP);
            }

            itemDetail.setRewardPoints(totalPointsPerItem);

            itemDetails.add(itemDetail);

            totalPricePerOrder = totalPricePerOrder.add(totalPricePerItem);
            totalPointsPerOrder = totalPricePerOrder.add(totalPointsPerItem);
        }

        orderDetail.setItemDetailModels(itemDetails);
        orderDetail.setTotalPrice(totalPricePerOrder);
        orderDetail.setRewardPoints(totalPointsPerOrder);
        return orderDetail;
    }

    @Override
    @Transactional
    public void restore(Item[] items) {
        for (Item item : items) {
            productDao.increaseStock(item.getProductId(), item.getQuantity());
        }
    }

}
