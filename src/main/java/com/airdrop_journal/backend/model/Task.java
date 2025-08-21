package com.airdrop_journal.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private String project;
    private boolean completed;
    private boolean isDaily;
    private Priority priority;
    private Instant dueDate;
    private Instant completedAt;
    private String notes;
    private String airdropId; // Reference to Airdrop
    @Field("user")
    private String userId; // Reference to User
    private List<String> tags;
    private Category category;
    private Integer estimatedTime; // in minutes
    private Difficulty difficulty;
    private String reward;
    private Instant createdAt;
    private Instant updatedAt;

    public enum Priority { LOW, MEDIUM, HIGH }
    public enum Category { TESTNET, MAINNET, SOCIAL, DEFI, GAMING, NFT, BRIDGE, STAKING }
    public enum Difficulty { EASY, MEDIUM, HARD }
}