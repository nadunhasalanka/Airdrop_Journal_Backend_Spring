package com.airdrop_journal.backend.dtos.task;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder // Using builder for easier construction in the service
public class TaskStatsResponse {
    private long total;
    private long completed;
    private long pending;
    private long daily;
    private int completionPercentage;
    private Map<String, Long> byCategory;
    private Map<String, Long> byProject;
}
