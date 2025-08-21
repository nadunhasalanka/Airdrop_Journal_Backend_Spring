package com.airdrop_journal.backend.controllers;


import com.airdrop_journal.backend.dtos.airdrop.AirdropRequest;
import com.airdrop_journal.backend.dtos.airdrop.AirdropResponse;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.services.AirdropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airdrops")
@RequiredArgsConstructor
public class AirdropController {

    private final AirdropService airdropService;

    @PostMapping
    public ResponseEntity<AirdropResponse> createAirdrop(
            @Valid @RequestBody AirdropRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        AirdropResponse response = airdropService.createAirdrop(request, currentUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // NEW ENDPOINT: GET /api/airdrops
    @GetMapping
    public ResponseEntity<List<AirdropResponse>> getMyAirdrops(
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(airdropService.getAirdropsForUser(currentUser));
    }

    // NEW ENDPOINT: GET /api/airdrops/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AirdropResponse> getAirdropById(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(airdropService.getAirdropById(id, currentUser));
    }

    // NEW ENDPOINT: PUT /api/airdrops/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AirdropResponse> updateAirdrop(
            @PathVariable String id,
            @Valid @RequestBody AirdropRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(airdropService.updateAirdrop(id, request, currentUser));
    }

    // NEW ENDPOINT: DELETE /api/airdrops/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirdrop(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser
    ) {
        airdropService.deleteAirdrop(id, currentUser);
        return ResponseEntity.noContent().build(); // Standard response for a successful delete
    }
}
