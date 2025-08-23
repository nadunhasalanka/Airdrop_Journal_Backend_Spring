package com.airdrop_journal.backend.dtos.user;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateRequest {

    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @Size(min = 3, max = 30)
    private String username;

    @Size(max = 500)
    private String bio;

}
