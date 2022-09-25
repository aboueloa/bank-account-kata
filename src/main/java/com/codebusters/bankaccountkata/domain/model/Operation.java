package com.codebusters.bankaccountkata.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Operation {
     private String clientId;
     private int amount;
     private int balance;
     private OperationType operation;
     private Instant operationDate;
}
