package com.airdrop_journal.backend.services;


import com.airdrop_journal.backend.dtos.auth.AuthResponse;
import com.airdrop_journal.backend.dtos.auth.LoginRequest;
import com.airdrop_journal.backend.dtos.auth.SignUpRequest;
import com.airdrop_journal.backend.dtos.user.UserResponse;
import com.airdrop_journal.backend.mappers.UserMapper;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.model.UserTag;
import com.airdrop_journal.backend.repositories.UserRepository;
import com.airdrop_journal.backend.repositories.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserTagRepository userTagRepository;

    public UserResponse signup(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        if (request.getUsername() != null && userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // 3. Hash the password
                .role(User.Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        createDefaultTagsForUser(savedUser);

        String jwtToken = jwtService.generateToken(savedUser);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userMapper.toUserResponse(savedUser))
                .build().getUser();
    }

    public AuthResponse login(LoginRequest request) {
        // This will internally use our userDetailsService and passwordEncoder to validate the credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If the above line does not throw an exception, the user is authenticated
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found after authentication"));

        // Generate a token for the authenticated user
        String jwtToken = jwtService.generateToken(user);

        // Build and return the AuthResponse DTO
        return AuthResponse.builder()
                .token(jwtToken)
                .user(userMapper.toUserResponse(user))
                .build();
    }

    private void createDefaultTagsForUser(User user) {
        List<UserTag> defaultTags = List.of(
                UserTag.builder().name("layer 2").color("#8B5CF6").isDefault(true).userId(user.getId()).build(),
                UserTag.builder().name("defi").color("#FF6B35").isDefault(true).userId(user.getId()).build(),
                UserTag.builder().name("testnet").color("#10B981").isDefault(true).userId(user.getId()).build(),
                UserTag.builder().name("mainnet").color("#F59E0B").isDefault(true).userId(user.getId()).build(),
                UserTag.builder().name("high priority").color("#EF4444").isDefault(true).userId(user.getId()).build()
                // Add any other default tags here
        );
        userTagRepository.saveAll(defaultTags);
    }

}