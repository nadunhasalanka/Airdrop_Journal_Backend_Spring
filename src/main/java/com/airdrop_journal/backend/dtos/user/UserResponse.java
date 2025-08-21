package com.airdrop_journal.backend.dtos.user;

import lombok.Data;
import com.airdrop_journal.backend.model.User;

import java.time.Instant;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String avatar;
    private User.Role role;
    private Instant createdAt;
}