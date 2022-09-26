package com.codebusters.bankaccountkata.persistence.repository;

import com.codebusters.bankaccountkata.domain.model.Account;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Data
public class AccountRepository {
    private final static Map<String, Integer> ACCOUNTS = new HashMap<>();

    public void deposit(int amount, String clientId) {
        int balance = ACCOUNTS.getOrDefault(clientId, 0);
        ACCOUNTS.put(clientId, balance + amount);
    }

    public Account findByAccountId(String accountId) {
        return Account.builder().balance(ACCOUNTS.get(accountId)).clientId(accountId).build();
    }

    public boolean contains(String clientId) {
        return ACCOUNTS.containsKey(clientId);
    }
}