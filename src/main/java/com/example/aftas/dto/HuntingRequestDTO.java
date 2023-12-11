package com.example.aftas.dto;

import com.example.aftas.model.Competition;
import com.example.aftas.model.Fish;
import com.example.aftas.model.Hunting;
import com.example.aftas.model.Member;

public record HuntingRequestDTO(
        Long competitionId,
        Long memberId,
        Long fishId
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .fish(Fish.builder().id(fishId).build())
                .build();
    }
}
