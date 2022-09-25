package com.codebusters.bankaccountkata.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    private String clientId;
    private int amount;
}
