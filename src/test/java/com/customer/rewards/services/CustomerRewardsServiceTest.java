package com.customer.rewards.services;

import com.customer.rewards.model.CustomerRewards;
import com.customer.rewards.services.impl.CustomerRewardsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@TestPropertySource(locations = "classpath:rewards-threshold.properties")
public class CustomerRewardsServiceTest {

    @Autowired
    private CustomerRewardsServiceImpl customerRewardsService;


    @Test
    public void returnsZeroRewardPointsIfNoCustomerTransactions() {

        CustomerRewards customerRewards = customerRewardsService.getCustomerRewards(102L);

        Assertions.assertEquals(0,customerRewards.getTotalRewards());
    }

    @Test
    public void returnsRewardPointsForGivenCustomerTransactions() {

        CustomerRewards rewardPoints = customerRewardsService.getCustomerRewards(1001L);

        Assertions.assertEquals(90, rewardPoints.getTotalRewards());
    }

}
