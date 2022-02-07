package com.hignnote.payments.util;

import java.time.LocalDate;
import java.util.Map;

public class ValidationUtil {

    public static boolean isValidTxn(Map<String, String> dataElementMap) {
        /* When Zip code is provided, a transaction is approved if amount is less than $200
         * and Expiration Date is greater than the current date */
        if(dataElementMap.containsKey("zip")
                && dataElementMap.get("zip") != null
                && dataElementMap.get("zip").length() == 5) {
            long amt = TransactionUtil.getDollarAmt(dataElementMap.get("amt"));

            return amt < 200 && isValidExpirationDate(dataElementMap.get("exp"));
        }
        /* When Zip code is not provided, a transaction is approved if amount is less than $100
         * and Expiration Date is greater than the current date */
        if(!dataElementMap.containsKey("zip")) {
            long amt = TransactionUtil.getDollarAmt(dataElementMap.get("amt"));
            return amt < 100  && isValidExpirationDate(dataElementMap.get("exp"));
        }

        return false;
    }

    private static boolean isValidExpirationDate(String expDateStr) {
        // MMYY
        int expYY = Integer.parseInt(expDateStr.substring(2));
        int expMM = Integer.parseInt(expDateStr.substring(0,2));

        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear()%100; //get last 2 digits only e.g. for 2021 get 21 only

        if(expYY > currentYear) return true;
        if (expYY == currentYear && expMM > currentMonth) return true;

        return false;
    }
}
