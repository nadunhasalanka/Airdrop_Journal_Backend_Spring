package com.airdrop_journal.backend.controllers;

import com.airdrop_journal.backend.dtos.user.AccountDeactivationRequest;
import com.airdrop_journal.backend.dtos.user.ProfileUpdateRequest;
import com.airdrop_journal.backend.dtos.user.UserResponse;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getUserProfile(currentUser));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserProfile(currentUser, request));
    }

    @DeleteMapping("/account")
    public ResponseEntity<Void> deactivateMyAccount(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody AccountDeactivationRequest request
    ) {
        userService.deactivateAccount(currentUser, request);
        return ResponseEntity.noContent().build();
    }
}
