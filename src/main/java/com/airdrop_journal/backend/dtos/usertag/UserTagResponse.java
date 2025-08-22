package com.airdrop_journal.backend.dtos.usertag;

import lombok.Data;
import java.time.Instant;

@Data
public class UserTagResponse {
    private String id;
    private String name;
    private String color;
    private String userId;
    private boolean isDefault;
    private int usageCount;
    private Instant createdAt;
}
