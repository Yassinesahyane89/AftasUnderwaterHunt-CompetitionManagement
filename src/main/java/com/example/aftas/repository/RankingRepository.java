package com.example.aftas.repository;

import com.example.aftas.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Ranking findByMemberIdAndCompetitionId(Long memberId, Long competitionId);
}
