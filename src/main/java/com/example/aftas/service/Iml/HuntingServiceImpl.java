package com.example.aftas.service.Iml;

import com.example.aftas.model.Hunting;
import com.example.aftas.model.Ranking;
import com.example.aftas.repository.HuntingRepository;
import com.example.aftas.service.HuntingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuntingServiceImpl implements HuntingService {
    private HuntingRepository huntingRepository;
    private FishServiceImpl fishService;
    private MemberServiceImpl memberService;
    private CompetitionServiceImpl competitionService;
    private RankingServiceImpl rankingService;

    public HuntingServiceImpl(HuntingRepository huntingRepository, FishServiceImpl fishService, MemberServiceImpl memberService, CompetitionServiceImpl competitionService, RankingServiceImpl rankingService) {
        this.huntingRepository = huntingRepository;
        this.fishService = fishService;
        this.memberService = memberService;
        this.competitionService = competitionService;
        this.rankingService = rankingService;
    }

    @Override
    public Hunting getHuntingById(Long id) {
        return huntingRepository.findById(id).orElseThrow(() -> new RuntimeException("Hunting id " + id + " not found"));
    }

    @Override
    public Hunting addHuntingResult(Hunting hunting) {
        Long competitionId = hunting.getCompetition().getId();
        Long memberId = hunting.getMember().getId();
        Long fishId = hunting.getFish().getId();
        // check if competition exist
        competitionService.getCompetitionById(competitionId);
        // check if member exist
        memberService.getMemberById(memberId);
        // check if fish exist
        fishService.getFishById(fishId);
        // check if Member has already participated in this competition
        rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        // check if fish has already been caught by this member in this competition if yes acquirement the number of fish caught
        Hunting existingHunting = huntingRepository.findByCompetitionIdAndMemberIdAndFishId(competitionId, memberId, fishId);

        Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        ranking.setScore(ranking.getScore() + hunting.getFish().getLevel().getPoint());
        rankingService.updateRanking(ranking, ranking.getId());

        if(existingHunting != null) {
            existingHunting.setNomberOfFish(existingHunting.getNomberOfFish() + 1);
            return huntingRepository.save(existingHunting);
        } else {
            return huntingRepository.save(hunting);
        }
    }

    @Override
    public List<Hunting> getHuntingsByCompetition(Long competitionId) {
        return huntingRepository.findByCompetitionId(competitionId);
    }

    @Override
    public List<Hunting> getHuntingsByCompetitionAndMember(Long competitionId, Long memberId) {
        return huntingRepository.findByCompetitionIdAndMemberId(competitionId, memberId);
    }

    @Override
    public void deleteHunting(Long id) {
        getHuntingById(id);
        huntingRepository.deleteById(id);
    }
}
