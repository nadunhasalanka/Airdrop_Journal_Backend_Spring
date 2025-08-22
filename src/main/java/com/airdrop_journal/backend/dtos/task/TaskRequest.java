package com.airdrop_journal.backend.dtos.task;

import com.airdrop_journal.backend.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Project name is required")
    @Size(max = 100)
    private String project;

    private boolean completed;
    private boolean isDaily;
    private Task.Priority priority;
    private Instant dueDate;
    private String notes;
    private String airdropId;
    private List<String> tags;
    private Task.Category category;
    private Integer estimatedTime;
    private Task.Difficulty difficulty;
    private String reward;
}
