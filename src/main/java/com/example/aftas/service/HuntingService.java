package com.example.aftas.service;

import com.example.aftas.model.Hunting;

import java.util.List;

public interface HuntingService {
    Hunting addHuntingResult(Hunting hunting);
    List<Hunting> getHuntingsByCompetition(Long competitionId);
    List<Hunting> getHuntingsByCompetitionAndMember(Long competitionId, Long memberId);
}
