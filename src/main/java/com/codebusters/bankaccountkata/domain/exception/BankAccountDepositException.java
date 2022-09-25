package com.codebusters.bankaccountkata.domain.exception;

import com.codebusters.bankaccountkata.domain.model.Operation;
import lombok.Data;
import lombok.Getter;

@Getter
public class BankAccountDepositException extends Exception{
    private final Operation operation;
    public BankAccountDepositException(String msg, Operation operation) {
        super(msg);
        this.operation = operation;
    }
}
