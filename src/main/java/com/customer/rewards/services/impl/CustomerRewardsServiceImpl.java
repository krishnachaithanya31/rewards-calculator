package com.customer.rewards.services.impl;

import com.customer.rewards.model.entity.CustomerTransaction;
import com.customer.rewards.model.dto.CustomerRewards;
import com.customer.rewards.repositories.CustomerTransactionRepository;
import com.customer.rewards.services.CustomerRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.customer.rewards.util.DateUtil.getDatePriorToDays;

@Service
public class CustomerRewardsServiceImpl implements CustomerRewardsService {

    private static final int TWO_POINTS = 2;
    private static final int THIRTY_DAY_OFFSET = 30;
    private static final int SIXTY_DAY_OFFSET = 60;
    private static final int NINETY_DAY_OFFSET = 90;

    @Value("${rewards.single.point.amount}")
    private Integer singlePointAmount;

    @Value("${rewards.double.point.amount}")
    private Integer doublePointAmount;

    private final CustomerTransactionRepository customerTransactionRepository;

    @Autowired
    public CustomerRewardsServiceImpl(CustomerTransactionRepository customerTransactionRepository) {
        this.customerTransactionRepository = customerTransactionRepository;
    }

    @Override
    public CustomerRewards getCustomerRewards(Long customerId) {

        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysOffsetDate = getDatePriorToDays(THIRTY_DAY_OFFSET);
        LocalDate sixtyDaysOffsetDate = getDatePriorToDays(SIXTY_DAY_OFFSET);
        LocalDate ninetyDaysOffsetDate = getDatePriorToDays(NINETY_DAY_OFFSET);

        List<CustomerTransaction> thirtyDayTransactions = customerTransactionRepository
                .findByCustomerIdAndTransactionDateBetween(customerId, thirtyDaysOffsetDate, currentDate);
        List<CustomerTransaction> sixtyDayTransactions = customerTransactionRepository
                .findByCustomerIdAndTransactionDateBetween(customerId, sixtyDaysOffsetDate, thirtyDaysOffsetDate);
        List<CustomerTransaction> ninetyDayTransactions = customerTransactionRepository
                .findByCustomerIdAndTransactionDateBetween(customerId, ninetyDaysOffsetDate, sixtyDaysOffsetDate);

        return calculateCustomerRewards(customerId, thirtyDayTransactions, sixtyDayTransactions, ninetyDayTransactions);

    }

    @Override
    public List<CustomerTransaction> getAllTransactionsByCustomerId(Long customerId) {
        try {
            return customerTransactionRepository.findByCustomerId(customerId);
        } catch (Exception throwableException) {
            throw throwableException;
        }
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        try {
            customerTransactionRepository.deleteByCustomerId(customerId);
        } catch (Exception throwableException) {
            throw throwableException;
        }
    }

    private Long calculateRewardPoints(CustomerTransaction customerTransaction) {
        long rewardPoints = 0;

        if (customerTransaction.getTransactionAmount() > singlePointAmount &&
                customerTransaction.getTransactionAmount() <= doublePointAmount) {
            rewardPoints = Math.round(customerTransaction.getTransactionAmount() - singlePointAmount);
        } else if (customerTransaction.getTransactionAmount() > doublePointAmount) {
            rewardPoints = singlePointAmount + TWO_POINTS * Math.round(
                    customerTransaction.getTransactionAmount() - doublePointAmount);
        }

        return rewardPoints;
    }

    private CustomerRewards calculateCustomerRewards(Long customerId, List<CustomerTransaction> thirtyDayTransactions,
                                                     List<CustomerTransaction> sixtyDayTransactions,
                                                     List<CustomerTransaction> ninetyDayTransactions) {
        CustomerRewards customerRewards = new CustomerRewards();
        customerRewards.setCustomerId(customerId);
        customerRewards.setCurrentMonthRewardPoints(calculate(thirtyDayTransactions));
        customerRewards.setPreviousMonthRewardPoints(calculate(sixtyDayTransactions));
        customerRewards.setThirdMonthRewardPoints(calculate(ninetyDayTransactions));
        customerRewards.setTotalRewards(customerRewards.getCurrentMonthRewardPoints()
                + customerRewards.getPreviousMonthRewardPoints() + customerRewards.getThirdMonthRewardPoints());
        return customerRewards;
    }

    private Long calculate(List<CustomerTransaction> customerTransactions) {
        return customerTransactions.stream()
                .map(this::calculateRewardPoints)
                .mapToLong(rewards -> rewards).sum();
    }

}
