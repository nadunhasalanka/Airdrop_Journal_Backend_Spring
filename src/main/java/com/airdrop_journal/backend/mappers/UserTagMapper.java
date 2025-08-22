package com.airdrop_journal.backend.mappers;

import com.airdrop_journal.backend.dtos.usertag.UserTagResponse;
import com.airdrop_journal.backend.model.UserTag;
import org.springframework.stereotype.Component;

@Component
public class UserTagMapper {

    public UserTagResponse toUserTagResponse(UserTag userTag) {
        if (userTag == null) return null;
        UserTagResponse response = new UserTagResponse();
        response.setId(userTag.getId());
        response.setName(userTag.getName());
        response.setColor(userTag.getColor());
        response.setUserId(userTag.getUserId());
        response.setDefault(userTag.isDefault());
        response.setUsageCount(userTag.getUsageCount());
        response.setCreatedAt(userTag.getCreatedAt());
        return response;
    }
}
