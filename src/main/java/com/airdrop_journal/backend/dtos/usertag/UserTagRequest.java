package com.airdrop_journal.backend.dtos.usertag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserTagRequest {

    @NotBlank(message = "Tag name is required")
    @Size(min = 1, max = 30, message = "Tag name must be between 1 and 30 characters")
    private String name;

    @Pattern(regexp = "^#[0-9A-F]{6}$", message = "Color must be a valid hex color code (e.g., #8B5CF6)")
    private String color;
}
