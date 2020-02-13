package com.customer.rewards.controllers;

import com.customer.rewards.model.entity.CustomerTransaction;
import com.customer.rewards.model.dto.CustomerRewards;
import com.customer.rewards.services.CustomerRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRewardsController {

    private final CustomerRewardsService customerRewardsService;

    @Autowired
    public CustomerRewardsController(CustomerRewardsService customerRewardsService) {
        this.customerRewardsService = customerRewardsService;
    }

    @GetMapping(value = "/{customerId}/rewards")
    public ResponseEntity<CustomerRewards> getCustomerRewards(@PathVariable("customerId") Long customerId) {

        return new ResponseEntity<>(
                customerRewardsService.getCustomerRewards(customerId),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{customerId}/transactions")
    public ResponseEntity<List<CustomerTransaction>> getAllTransactionsByCustomerId(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(
                customerRewardsService.getAllTransactionsByCustomerId(customerId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity deleteCustomerTransactionsById(@PathVariable("customerId") Long customerId) {
        customerRewardsService.deleteByCustomerId(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }

}