package com.hignnote.payments.util;

public class TransactionUtil {

    public static long getDollarAmt(String amtStr) {
        return Long.parseLong(amtStr)/100;
    }

    public static char[] toBinary(String bitmap) {
        int decimal = Integer.parseInt(bitmap, 16);
        // 0 padding
        String binary = String.format("%8s", Integer.toBinaryString(decimal)).replace(' ', '0');
        return binary.toCharArray();
    }

    public static String toHex(String binaryStr) {
        int decimal = Integer.parseInt(binaryStr,2);
        String hexStr = Integer.toString(decimal,16);
        return hexStr;
    }
}
