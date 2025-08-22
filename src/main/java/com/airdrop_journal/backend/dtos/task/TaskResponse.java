package com.airdrop_journal.backend.dtos.task;

import com.airdrop_journal.backend.model.Task;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private String project;
    private boolean completed;
    private boolean isDaily;
    private Task.Priority priority;
    private Instant dueDate;
    private Instant completedAt;
    private String notes;
    private String airdropId;
    private String userId;
    private List<String> tags;
    private Task.Category category;
    private Integer estimatedTime;
    private Task.Difficulty difficulty;
    private String reward;
    private Instant createdAt;
    private Instant updatedAt;
}
