package com.codebusters.bankaccountkata.domain.service;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import lombok.AllArgsConstructor;

import java.util.Objects;
@AllArgsConstructor
public class BankAccountService {
    private final BankAccountPort bankAccountPort;

    public History makeDeposit(Transaction transaction) {
        return null;
    }
}
