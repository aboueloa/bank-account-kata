package com.codebusters.bankaccountkata.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class OperationRequestDTO {
    @NotBlank(message = "client id must be not null")
    private String clientId;
    @Min(value = 0, message = "amount must be > 0")
    private int amount;
}
