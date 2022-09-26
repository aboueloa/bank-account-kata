package com.codebusters.bankaccountkata.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
     private int amount;
     private int balance;
     private OperationType operation;
     private Instant operationDate;
}
