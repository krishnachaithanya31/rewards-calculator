package com.customer.rewards.util;

import java.time.LocalDate;

public class DateUtil {

    public static LocalDate getDatePriorToDays(int days) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.minusDays(days);
    }

}
