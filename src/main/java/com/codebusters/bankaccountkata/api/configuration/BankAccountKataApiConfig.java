package com.codebusters.bankaccountkata.api.configuration;

import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAccountKataApiConfig {
    @Bean
    BankAccountService bankAccountService(BankAccountPort bankAccountPort) {
        return new BankAccountService(bankAccountPort);
    }
}
