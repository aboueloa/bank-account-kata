package com.codebusters.bankaccountkata.domain.model;

import lombok.Data;

@Data
public class Transaction {
    private String clientId;
    private int amount;
}
