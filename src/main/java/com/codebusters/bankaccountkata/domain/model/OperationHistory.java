package com.codebusters.bankaccountkata.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OperationHistory {
    private List<Operation> operations;
    private int balance;
}
