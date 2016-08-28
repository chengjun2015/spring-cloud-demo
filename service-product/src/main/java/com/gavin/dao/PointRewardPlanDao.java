package com.gavin.dao;

import com.gavin.domain.product.PointRewardPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRewardPlanDao {

    PointRewardPlan searchApplicativeByProductId(@Param("productId") Long productId);
}
