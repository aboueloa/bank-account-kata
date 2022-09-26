package com.codebusters.bankaccountkata.api.controller;

import com.codebusters.bankaccountkata.api.dto.OperationRequest;
import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bank-operation")
@Slf4j
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping(path = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> moneyDeposit(@Valid @RequestBody OperationRequest operationRequest) throws BankAccountDepositException {
        log.info("Making a deposit");
        return ResponseEntity.ok(bankAccountService.makeDeposit(Transaction.builder()
                .amount(operationRequest.getAmount())
                .clientId(operationRequest.getClientId())
                .build()));
    }

    @PostMapping(path= "/withdrawal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> moneyWithdrawal(@Valid @RequestBody OperationRequest operationRequest) {
        log.info("withdrawal of money");
        return ResponseEntity.ok(bankAccountService.withdrawal(Transaction.builder()
                .amount(operationRequest.getAmount())
                .clientId(operationRequest.getClientId())
                .build()));
    }
}
