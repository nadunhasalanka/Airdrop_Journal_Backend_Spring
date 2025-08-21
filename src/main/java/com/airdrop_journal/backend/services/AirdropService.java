package com.airdrop_journal.backend.services;

import com.airdrop_journal.backend.dtos.airdrop.AirdropRequest;
import com.airdrop_journal.backend.dtos.airdrop.AirdropResponse;
import com.airdrop_journal.backend.mappers.AirdropMapper;
import com.airdrop_journal.backend.model.Airdrop;
import com.airdrop_journal.backend.model.User;
import com.airdrop_journal.backend.repositories.AirdropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirdropService {

    private final AirdropRepository airdropRepository;
    private final AirdropMapper airdropMapper;

    public AirdropResponse createAirdrop(AirdropRequest request, User currentUser) {
        Airdrop airdrop = airdropMapper.toAirdrop(request);

        airdrop.setUserId(currentUser.getId());
        airdrop.setCreatedAt(Instant.now());
        airdrop.setUpdatedAt(Instant.now());

        Airdrop savedAirdrop = airdropRepository.save(airdrop);

        return airdropMapper.toAirdropResponse(savedAirdrop);
    }

    // NEW METHOD: Get all airdrops for the current user
    public List<AirdropResponse> getAirdropsForUser(User currentUser) {
        return airdropRepository.findByUserId(currentUser.getId())
                .stream()
                .map(airdropMapper::toAirdropResponse)
                .collect(Collectors.toList());
    }

    // NEW METHOD: Get a single airdrop by ID for the current user
    public AirdropResponse getAirdropById(String airdropId, User currentUser) {
        Airdrop airdrop = airdropRepository.findByIdAndUserId(airdropId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Airdrop not found or you do not have permission to view it."));
        return airdropMapper.toAirdropResponse(airdrop);
    }

    // NEW METHOD: Update an airdrop
    @Transactional
    public AirdropResponse updateAirdrop(String airdropId, AirdropRequest request, User currentUser) {
        // First, find the existing airdrop and ensure it belongs to the user
        Airdrop existingAirdrop = airdropRepository.findByIdAndUserId(airdropId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Airdrop not found or you do not have permission to update it."));

        // Update the fields from the request DTO
        existingAirdrop.setName(request.getName());
        existingAirdrop.setDescription(request.getDescription());
        existingAirdrop.setEcosystem(request.getEcosystem());
        existingAirdrop.setType(request.getType());
        existingAirdrop.setStatus(request.getStatus());
        existingAirdrop.setDeadline(request.getDeadline());
        existingAirdrop.setEstimatedValue(request.getEstimatedValue());
        existingAirdrop.setPriority(request.getPriority());
        existingAirdrop.setOfficialLink(request.getOfficialLink());
        // ... set other fields as needed ...
        existingAirdrop.setUpdatedAt(Instant.now());

        // Save the updated airdrop
        Airdrop updatedAirdrop = airdropRepository.save(existingAirdrop);

        return airdropMapper.toAirdropResponse(updatedAirdrop);
    }

    // NEW METHOD: Delete an airdrop
    @Transactional
    public void deleteAirdrop(String airdropId, User currentUser) {
        // First, check if the airdrop exists and belongs to the user
        if (!airdropRepository.existsByIdAndUserId(airdropId, currentUser.getId())) {
            throw new RuntimeException("Airdrop not found or you do not have permission to delete it.");
        }

        // TODO: We need to also delete all associated tasks, just like in your Node.js code
        // taskRepository.deleteByAirdropIdAndUserId(airdropId, currentUser.getId());

        airdropRepository.deleteById(airdropId);
    }

}
