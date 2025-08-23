package com.airdrop_journal.backend.services;

import com.airdrop_journal.backend.dtos.user.AccountDeactivationRequest;
import com.airdrop_journal.backend.dtos.user.ProfileUpdateRequest;
import com.airdrop_journal.backend.dtos.user.UserResponse;
import com.airdrop_journal.backend.mappers.UserMapper;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserProfile(User currentUser) {
        // The user object from @AuthenticationPrincipal is already up-to-date
        return userMapper.toUserResponse(currentUser);
    }

    @Transactional
    public UserResponse updateUserProfile(User currentUser, ProfileUpdateRequest request) {
        // Check if username is being updated and if it's already taken
        if (request.getUsername() != null && !request.getUsername().equals(currentUser.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username is already taken.");
            }
            currentUser.setUsername(request.getUsername());
        }

        // Update other fields if they are provided in the request
        if (request.getFirstName() != null) {
            currentUser.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            currentUser.setLastName(request.getLastName());
        }
        if (request.getBio() != null) {
            currentUser.setBio(request.getBio());
        }

        User updatedUser = userRepository.save(currentUser);
        return userMapper.toUserResponse(updatedUser);
    }

    @Transactional
    public void deactivateAccount(User currentUser, AccountDeactivationRequest request) {
        // 1. Verify password
        if (!passwordEncoder.matches(request.getConfirmPassword(), currentUser.getPassword())) {
            throw new RuntimeException("Password is incorrect.");
        }

        // 2. Deactivate account (soft delete)
        currentUser.setActive(false);
        // Mangle email and username to free them up for future registrations
        currentUser.setEmail("deleted_" + System.currentTimeMillis() + "_" + currentUser.getEmail());
        if (currentUser.getUsername() != null) {
            currentUser.setUsername("deleted_" + System.currentTimeMillis() + "_" + currentUser.getUsername());
        }

        userRepository.save(currentUser);
    }
}