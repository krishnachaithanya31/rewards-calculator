package com.customer.rewards.services;

import com.customer.rewards.model.dto.CustomerRewards;
import com.customer.rewards.services.impl.CustomerRewardsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:rewards-threshold.properties")
class CustomerRewardsServiceTest {

    @Autowired
    private CustomerRewardsServiceImpl customerRewardsService;


    @Test
    void returnsZeroRewardPointsIfNoCustomerTransactions() {

        CustomerRewards customerRewards = customerRewardsService.getCustomerRewards(102L);

        Assertions.assertEquals(0,customerRewards.getTotalRewards());
    }

    @Test
    void returnsRewardPointsForGivenCustomerTransactions() {

        CustomerRewards rewardPoints = customerRewardsService.getCustomerRewards(1001L);

        Assertions.assertEquals(90, rewardPoints.getTotalRewards());
    }

}
