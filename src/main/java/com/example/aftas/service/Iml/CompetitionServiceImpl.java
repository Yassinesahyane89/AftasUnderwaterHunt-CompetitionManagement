package com.example.aftas.service.Iml;

import com.example.aftas.handlers.exception.ResourceNotFoundException;
import com.example.aftas.model.Competition;
import com.example.aftas.model.Member;
import com.example.aftas.model.Ranking;
import com.example.aftas.repository.CompetitionRepository;
import com.example.aftas.service.CompetitionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final MemberServiceImpl memberService;
    private final RankingServiceImpl rankingService;


    public CompetitionServiceImpl(CompetitionRepository competitionRepository, MemberServiceImpl memberService, RankingServiceImpl rankingService) {
        this.competitionRepository = competitionRepository;
        this.memberService = memberService;
        this.rankingService = rankingService;
    }
    @Override
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Competition id " + id + " not found"));
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public List<Competition> getCompetitionsByStatus(String status) {
        return null;
    }

    @Override
    public Competition addCompetition(Competition competition) {

        // here i want to check that Every day there can be only one competition
        Competition competition1 = competitionRepository.findByDate(competition.getDate());
        if (competition1 != null) {
            throw new RuntimeException("There is already a competition on " + competition.getDate());
        }

        // here i want to check that Competition start time must be before end time
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // here i want to generate a unique code for the competition from that date and location  pattern: ims-22-12-23, ims is the third first letters of the location
        String code = competition.getLocation().substring(0, 3) + "-" + competition.getDate().getDay() + "-" + competition.getDate().getMonth() + "-" + competition.getDate().getYear();

        competition.setCode(code);
        return competitionRepository.save(competition);

    }

    @Override
    public Competition updateCompetition(Competition competition, Long id) {
        Competition existingCompetition = getCompetitionById(id);
        existingCompetition.setDate(competition.getDate());
        existingCompetition.setStartTime(competition.getStartTime());
        existingCompetition.setEndTime(competition.getEndTime());
        existingCompetition.setLocation(competition.getLocation());
        existingCompetition.setAmount(competition.getAmount());
        return competitionRepository.save(existingCompetition);
    }

    @Override
    public void deleteCompetition(Long id) {
        getCompetitionById(id);
        competitionRepository.deleteById(id);
    }

    @Override
    public void registerMemberForCompetition(Long competitionId, Long memberId) {
        // here i want to check that the competition is exist
        Competition competition = getCompetitionById(competitionId);
        if(competition == null){
            throw new RuntimeException("Competition id " + competitionId + " not found");
        }
        // here i want to check that the member is exist
        Member member = memberService.getMemberById(memberId);
        if(member == null){
            throw new RuntimeException("Member id " + memberId + " not found");
        }
        // here i want to check that the member is not already registered for the competition
        if(competition.getRanking().stream().anyMatch(ranking -> ranking.getMember().getId().equals(memberId))){
            throw new RuntimeException("Member id " + memberId + " is already registered for the competition");
        }
        // here i want to check that the competition is not started yet and are still have 24 hours before the start time
        if(competition.getStartTime().isBefore(competition.getStartTime().minusDays(1))){
            throw new RuntimeException("Competition id " + competitionId + " is closed for registration");
        }

    }

    @Override
    public void recordCompetitionResult(Ranking ranking) {
        Long competitionId = ranking.getCompetition().getId();
        Long memberId = ranking.getMember().getId();
        int result = ranking.getScore();
        // here i want to check that the competition is exist
        Competition competition = getCompetitionById(competitionId);
        if(competition == null){
            throw new RuntimeException("Competition id " + competitionId + " not found");
        }
        // here i want to check that the member is exist
        Member member = memberService.getMemberById(memberId);
        if(member == null){
            throw new RuntimeException("Member id " + memberId + " not found");
        }
        // here i want to check that the member is registered for the competition
        if(competition.getRanking().stream().noneMatch(ranking1 -> ranking1.getMember().getId().equals(memberId))){
            throw new RuntimeException("Member id " + memberId + " is not registered for the competition");
        }
        // here i want to check that the competition is not passed 24 hours after the end time
        if(competition.getEndTime().isBefore(competition.getEndTime().plusDays(1))){
            throw new RuntimeException("Competition id " + competitionId + " is closed for registration");
        }
        // here i want to register the result for the member
        rankingService.addRanking(ranking);
    }
}