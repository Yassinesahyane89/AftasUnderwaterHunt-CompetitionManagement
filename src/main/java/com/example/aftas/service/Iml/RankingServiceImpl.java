package com.example.aftas.service.Iml;

import com.example.aftas.model.Ranking;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.RankingService;
import org.springframework.stereotype.Service;

@Service
public class RankingServiceImpl implements RankingService {
    private RankingRepository rankingRepository;

    public RankingServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }
    @Override
    public Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId) {
        Ranking ranking= rankingRepository.findByMemberIdAndCompetitionId(memberId, competitionId);
        if (ranking == null) {
            throw new RuntimeException("Member id " + memberId + " has not participated in competition id " + competitionId);
        }
        return ranking;
    }

    @Override
    public Ranking getRankingById(Long id) {
        return rankingRepository.findById(id).orElseThrow(() -> new RuntimeException("Ranking id " + id + " not found"));
    }
    @Override
    public Ranking addRanking(Ranking ranking) {
        return rankingRepository.save(ranking);
    }
    @Override
    public Ranking updateRanking(Ranking ranking, Long id) {
        Ranking existingRanking = getRankingById(id);
        existingRanking.setRank(ranking.getRank());
        existingRanking.setScore(ranking.getScore());
        return rankingRepository.save(existingRanking);
    }

    @Override
    public Ranking updateRankingScore(Ranking ranking, Long id) {
        Ranking existingRanking = getRankingById(id);
        existingRanking.setScore(ranking.getScore()+existingRanking.getScore());
        return rankingRepository.save(existingRanking);
    }

    @Override
    public void deleteRanking(Long id) {
        getRankingById(id);
        rankingRepository.deleteById(id);
    }
}
