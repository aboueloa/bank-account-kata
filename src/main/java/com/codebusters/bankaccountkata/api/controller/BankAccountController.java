package com.codebusters.bankaccountkata.api.controller;

import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-operation")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping(path = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> moneyDeposit(@RequestBody Transaction transaction){
        return ResponseEntity.ok().build();
    }
}
