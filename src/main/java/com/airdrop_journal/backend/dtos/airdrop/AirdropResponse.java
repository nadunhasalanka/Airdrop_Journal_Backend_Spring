package com.airdrop_journal.backend.dtos.airdrop;


import com.airdrop_journal.backend.model.Airdrop;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class AirdropResponse {
    private String id;
    private String name;
    private String description;
    private Airdrop.Ecosystem ecosystem;
    private Airdrop.AirdropType type;
    private String status;
    private String deadline;
    private String estimatedValue;
    private String priority;
    private String officialLink;
    private String referralLink;
    private String logoUrl;
    private String bannerUrl;
    private List<String> tags;
    private String notes;
    private boolean isDailyTask;
    private String dailyTaskNote;
    private String tokenSymbol;
    private Instant startDate;
    private Airdrop.SocialMedia socialMedia;
    private String userId;
    private Instant createdAt;
    private Instant updatedAt;
}