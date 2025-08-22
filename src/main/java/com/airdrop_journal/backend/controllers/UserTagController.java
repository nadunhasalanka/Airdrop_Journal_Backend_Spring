package com.airdrop_journal.backend.controllers;

import com.airdrop_journal.backend.dtos.usertag.UserTagRequest;
import com.airdrop_journal.backend.dtos.usertag.UserTagResponse;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.services.UserTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags") // Maps to /api/tags as in your Node.js app
@RequiredArgsConstructor
public class UserTagController {

    private final UserTagService userTagService;

    @PostMapping
    public ResponseEntity<UserTagResponse> createTag(
            @Valid @RequestBody UserTagRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return new ResponseEntity<>(userTagService.createTag(request, currentUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserTagResponse>> getMyTags(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userTagService.getTagsForUser(currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTagResponse> updateTag(
            @PathVariable String id,
            @Valid @RequestBody UserTagRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(userTagService.updateTag(id, request, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser
    ) {
        userTagService.deleteTag(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
