package com.hignnote.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    public void testApplication(){
        String[] arg = {"/Users/prabhupatel/githubprojects/PaymentTransactionPlatform/build/resources/main/sample/data.txt"};
        Application.main(arg);
    }
}