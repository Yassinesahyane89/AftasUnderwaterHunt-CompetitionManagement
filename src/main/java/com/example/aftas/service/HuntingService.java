package com.example.aftas.service;

import com.example.aftas.model.Hunting;

import java.util.List;

public interface HuntingService {
    void addHuntingResult(Hunting hunting);
    List<Hunting> getHuntingsByCompetition(Long competitionId);
    List<Hunting> getHuntingsByCompetitionAndMember(Long competitionId, Long memberId);
}
