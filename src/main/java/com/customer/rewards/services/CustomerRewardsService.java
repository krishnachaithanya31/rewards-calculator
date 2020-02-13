package com.customer.rewards.services;

import com.customer.rewards.model.entity.CustomerTransaction;
import com.customer.rewards.model.dto.CustomerRewards;

import java.util.List;

public interface CustomerRewardsService {

    CustomerRewards getCustomerRewards(Long customerId);

    List<CustomerTransaction> getAllTransactionsByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}
