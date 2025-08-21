package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data // Generates getters, setters, toString, etc.
@Builder // Implements the builder pattern
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // Maps this class to the "users" collection in MongoDB
public class User {

    @Id
    private String id; // Corresponds to MongoDB's _id

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true, sparse = true)
    private String username;

    private String password; // We won't store the password in plain text, of course

    private String avatar;
    private String bio;

    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private boolean isActive = true;

    // For password reset functionality
    private String passwordResetToken;
    private Instant passwordResetExpire;
    private Instant passwordChangedAt;

    // For login attempt tracking
    @Builder.Default
    private int loginAttempts = 0;
    private Instant lockUntil;
    private Instant lastLoginAt;

    // We will model nested objects as separate classes or Maps
    private Preferences preferences;
    private Stats stats;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant updatedAt;

    // Inner classes for nested objects
    @Data
    public static class Preferences {
        private String theme = "system";
        private boolean emailNotifications = true;
        private boolean airdropReminders = true;
        private String defaultCurrency = "USD";
    }

    @Data
    public static class Stats {
        private int totalAirdrops = 0;
        private int completedAirdrops = 0;
        private String totalRewards = "0";
    }

    public enum Role {
        USER,
        ADMIN
    }
}