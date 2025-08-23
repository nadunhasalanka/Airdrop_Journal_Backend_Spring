package com.airdrop_journal.backend.dtos.airdrop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirdropStatsResponse {
    private long total;
    private Map<String, Long> byStatus;
}
