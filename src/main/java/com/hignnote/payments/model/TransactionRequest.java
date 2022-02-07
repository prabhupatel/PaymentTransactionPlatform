package com.hignnote.payments.model;

import com.hignnote.payments.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.BitSet;

@Data
@Builder
@AllArgsConstructor
public class TransactionRequest {
    private MessageType messageType;
    private BitSet datBitField;
    private String pan;
    private Integer expiryDate;
    private Long transactionAmount;
    private String cardHolderName;
    private Integer zipCode;


}
