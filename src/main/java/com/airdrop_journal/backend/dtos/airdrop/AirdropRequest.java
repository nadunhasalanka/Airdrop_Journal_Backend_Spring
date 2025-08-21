package com.airdrop_journal.backend.dtos.airdrop;


import com.airdrop_journal.backend.model.Airdrop;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.List;

@Data
public class AirdropRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 1000)
    private String description;

    private Airdrop.Ecosystem ecosystem;
    private Airdrop.AirdropType type;
    private String status;
    private String deadline;
    private String estimatedValue;
    private String priority;

    @NotBlank(message = "Official link is required")
    @URL(message = "Official link must be a valid URL")
    private String officialLink;

    @URL(message = "Referral link must be a valid URL")
    private String referralLink;

    @URL(message = "Logo URL must be a valid URL")
    private String logoUrl;

    @URL(message = "Banner URL must be a valid URL")
    private String bannerUrl;

    private List<String> tags;
    private String notes;
    private boolean isDailyTask;
    private String dailyTaskNote;
    private String tokenSymbol;
    private Instant startDate;
    private Airdrop.SocialMedia socialMedia;
}
