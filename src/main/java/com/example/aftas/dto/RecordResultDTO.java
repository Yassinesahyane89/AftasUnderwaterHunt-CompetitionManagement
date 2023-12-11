package com.example.aftas.dto;

import com.example.aftas.model.Ranking;

public record RecordResultDTO(
        Long id,
        int score
) {
    public Ranking toRanking() {
        return Ranking.builder()
                .id(id)
                .score(score)
                .build();
    }
}
