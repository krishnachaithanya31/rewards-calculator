package com.customer.rewards.services;

import com.customer.rewards.domain.CustomerTransaction;
import com.customer.rewards.model.CustomerRewards;

import java.util.List;

public interface CustomerRewardsService {

    CustomerRewards getCustomerRewards(Long customerId);

    List<CustomerTransaction> getAllTransactionsByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}
