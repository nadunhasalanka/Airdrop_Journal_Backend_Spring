package com.airdrop_journal.backend.mappers;

import com.airdrop_journal.backend.dtos.airdrop.AirdropRequest;
import com.airdrop_journal.backend.dtos.airdrop.AirdropResponse;
import com.airdrop_journal.backend.model.Airdrop;
import org.springframework.stereotype.Component;

@Component
public class AirdropMapper {

    public Airdrop toAirdrop(AirdropRequest request) {
        if (request == null) {
            return null;
        }
        return Airdrop.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ecosystem(request.getEcosystem())
                .type(request.getType())
                .status(request.getStatus())
                .deadline(request.getDeadline())
                .estimatedValue(request.getEstimatedValue())
                .priority(request.getPriority())
                .officialLink(request.getOfficialLink())
                .referralLink(request.getReferralLink())
                .logoUrl(request.getLogoUrl())
                .bannerUrl(request.getBannerUrl())
                .tags(request.getTags())
                .notes(request.getNotes())
                .isDailyTask(request.isDailyTask())
                .dailyTaskNote(request.getDailyTaskNote())
                .tokenSymbol(request.getTokenSymbol())
                .startDate(request.getStartDate())
                .socialMedia(request.getSocialMedia())
                .build();
    }

    public AirdropResponse toAirdropResponse(Airdrop airdrop) {
        if (airdrop == null) {
            return null;
        }
        AirdropResponse response = new AirdropResponse();
        response.setId(airdrop.getId());
        response.setName(airdrop.getName());
        response.setDescription(airdrop.getDescription());
        response.setEcosystem(airdrop.getEcosystem());
        response.setType(airdrop.getType());
        response.setStatus(airdrop.getStatus());
        response.setDeadline(airdrop.getDeadline());
        response.setEstimatedValue(airdrop.getEstimatedValue());
        response.setPriority(airdrop.getPriority());
        response.setOfficialLink(airdrop.getOfficialLink());
        response.setReferralLink(airdrop.getReferralLink());
        response.setLogoUrl(airdrop.getLogoUrl());
        response.setBannerUrl(airdrop.getBannerUrl());
        response.setTags(airdrop.getTags());
        response.setNotes(airdrop.getNotes());
        response.setDailyTask(airdrop.isDailyTask());
        response.setDailyTaskNote(airdrop.getDailyTaskNote());
        response.setTokenSymbol(airdrop.getTokenSymbol());
        response.setStartDate(airdrop.getStartDate());
        response.setSocialMedia(airdrop.getSocialMedia());
        response.setUserId(airdrop.getUserId());
        response.setCreatedAt(airdrop.getCreatedAt());
        response.setUpdatedAt(airdrop.getUpdatedAt());
        return response;
    }
}
