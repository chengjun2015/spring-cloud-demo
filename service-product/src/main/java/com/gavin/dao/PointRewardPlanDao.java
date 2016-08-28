package com.gavin.dao;

import com.gavin.domain.product.PointRewardPlan;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRewardPlanDao {

    PointRewardPlan searchApplicativeByProductId(Long productId);
}
