package com.codebusters.bankaccountkata.api.controller;

import com.codebusters.bankaccountkata.api.dto.OperationRequestDTO;
import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
    public ResponseEntity<Operation> moneyDeposit(@Valid @RequestBody OperationRequestDTO operationRequestDTO) throws BankAccountException {
        log.info("Making a deposit for {}", operationRequestDTO);
        var transaction = OperationRequest.builder()
                .amount(operationRequestDTO.getAmount())
                .clientId(operationRequestDTO.getClientId())
                .build();
        var operation = bankAccountService.makeDeposit(transaction);
        return ResponseEntity.ok(operation);
    }

    @PostMapping(path= "/withdrawal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> moneyWithdrawal(@Valid @RequestBody OperationRequestDTO operationRequestDTO) throws BankAccountException {
        log.info("withdrawal of money");
        return ResponseEntity.ok(bankAccountService.withdrawal(OperationRequest.builder()
                .amount(operationRequestDTO.getAmount())
                .clientId(operationRequestDTO.getClientId())
                .build()));
    }

    @GetMapping(path = "/history/{client-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationHistory> getHistory(@PathVariable("client-id") @NotBlank String clientId) {
        return ResponseEntity.ok(new OperationHistory());
    }
}
