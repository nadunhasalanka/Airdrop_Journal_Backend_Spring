package model;

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
@Document(collection = "airdrops")
public class Airdrop {
    @Id
    private String id;

    private String name;
    private String description;
    private Ecosystem ecosystem;
    private AirdropType type;
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
    private SocialMedia socialMedia;

    // This is the link back to the User.
// We store the user's ID here.
    @Field("user")
    private String userId;

    private Instant createdAt;
    private Instant updatedAt;

    // Enums for type safety
    public enum Ecosystem {
        ETHEREUM, SOLANA, POLYGON, ARBITRUM, OPTIMISM, BSC, AVALANCHE, MULTI_CHAIN
    }

    public enum AirdropType {
        TESTNET, MAINNET, TELEGRAM, WEB3, SOCIAL
    }

    @Data
    public static class SocialMedia {
        private String twitter;
        private String telegram;
        private String discord;
        private String medium;
        private String github;
        private String website;
    }

}
