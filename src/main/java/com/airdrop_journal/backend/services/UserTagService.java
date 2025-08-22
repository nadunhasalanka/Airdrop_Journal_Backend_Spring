package com.airdrop_journal.backend.services;

import com.airdrop_journal.backend.dtos.usertag.UserTagRequest;
import com.airdrop_journal.backend.dtos.usertag.UserTagResponse;
import com.airdrop_journal.backend.mappers.UserTagMapper;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.model.UserTag;
import com.airdrop_journal.backend.repositories.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTagService {

    private final UserTagRepository userTagRepository;
    private final UserTagMapper userTagMapper;

    @Transactional
    public UserTagResponse createTag(UserTagRequest request, User currentUser) {
        // Check if a tag with the same name already exists for this user
        Optional<UserTag> existingTag = userTagRepository.findByNameAndUserId(request.getName().toLowerCase().trim(), currentUser.getId());
        if (existingTag.isPresent()) {
            throw new RuntimeException("Tag with this name already exists.");
        }

        UserTag newTag = UserTag.builder()
                .name(request.getName().toLowerCase().trim())
                .color(request.getColor() != null ? request.getColor() : "#8B5CF6") // Default color
                .userId(currentUser.getId())
                .isDefault(false)
                .usageCount(0)
                .createdAt(Instant.now())
                .build();

        UserTag savedTag = userTagRepository.save(newTag);
        return userTagMapper.toUserTagResponse(savedTag);
    }

    public List<UserTagResponse> getTagsForUser(User currentUser) {
        return userTagRepository.findByUserId(currentUser.getId())
                .stream()
                .map(userTagMapper::toUserTagResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserTagResponse updateTag(String tagId, UserTagRequest request, User currentUser) {
        UserTag existingTag = (UserTag) userTagRepository.findByIdAndUserId(tagId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Tag not found or you do not have permission."));

        if (existingTag.isDefault()) {
            throw new RuntimeException("Cannot modify a default tag.");
        }

        existingTag.setName(request.getName().toLowerCase().trim());
        if (request.getColor() != null) {
            existingTag.setColor(request.getColor());
        }

        UserTag updatedTag = userTagRepository.save(existingTag);
        return userTagMapper.toUserTagResponse(updatedTag);
    }

    @Transactional
    public void deleteTag(String tagId, User currentUser) {
        UserTag tagToDelete = (UserTag) userTagRepository.findByIdAndUserId(tagId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Tag not found or you do not have permission."));

        if (tagToDelete.isDefault()) {
            throw new RuntimeException("Cannot delete a default tag.");
        }

        userTagRepository.deleteById(tagId);
    }
}
