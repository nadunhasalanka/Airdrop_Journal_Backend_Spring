package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usertags")
public class UserTag {

    @Id
    private String id;
    private String name;
    private String color;
    @Field("userId")
    private String userId; // Storing the ID of the user
    private boolean isDefault;
    private int usageCount;
    private Instant createdAt;
    private Instant updatedAt;
}
