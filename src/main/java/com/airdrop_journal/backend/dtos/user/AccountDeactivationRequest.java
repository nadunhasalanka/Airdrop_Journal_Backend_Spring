package com.airdrop_journal.backend.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDeactivationRequest {

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}
