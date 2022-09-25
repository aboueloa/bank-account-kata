package com.codebusters.bankaccountkata.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class History {
     private String clientId;
     private int amount;
     private int balance;
     private Operation operation;
     private Instant operationDate;
}
