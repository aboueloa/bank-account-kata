package com.codebusters.bankaccountkata.persistence.repository;

import com.codebusters.bankaccountkata.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepository {
    public static Map<String, Integer> ACCOUNTS = new HashMap<>();

    public void deposit(int amount, String clientId) {

    }

    public Account findByAccountId(String accountId) {
        return Account.builder().build();
    }
}
