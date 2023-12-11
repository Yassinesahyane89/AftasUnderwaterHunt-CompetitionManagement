package com.example.aftas.controller;

import com.example.aftas.dto.HuntingRequestDTO;
import com.example.aftas.handlers.response.ResponseMessage;
import com.example.aftas.model.Hunting;
import com.example.aftas.service.HuntingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/huntings")
public class HuntingController {

    private final HuntingService huntingService;

    public HuntingController(HuntingService huntingService) {
        this.huntingService = huntingService;
    }

    // add hunting result
    @PostMapping
    public ResponseEntity addHuntingResult(HuntingRequestDTO huntingRequestDTO) {
        Hunting hunting = huntingService.addHuntingResult(huntingRequestDTO.toHunting());
        if(hunting == null) {
            return ResponseMessage.badRequest("Hunting result not added");
        }else {
            return ResponseMessage.created(hunting,"Hunting result added successfully");
        }
    }

    // get huntings by competition
    @GetMapping("/competition/{competitionId}")
    public ResponseEntity getHuntingsByCompetition(@PathVariable Long competitionId) {
        List<Hunting> huntings = huntingService.getHuntingsByCompetition(competitionId);
        if(huntings.isEmpty()) {
            return ResponseMessage.notFound("Huntings not found");
        }else {
            return ResponseMessage.ok(huntings, "Success");
        }
    }

    // get huntings by competition and member
    @GetMapping("/competition/{competitionId}/member/{memberId}")
    public ResponseEntity getHuntingsByCompetitionAndMember(@PathVariable Long competitionId, @PathVariable Long memberId) {
        List<Hunting> huntings = huntingService.getHuntingsByCompetitionAndMember(competitionId, memberId);
        if(huntings.isEmpty()) {
            return ResponseMessage.notFound("Huntings not found");
        }else {
            return ResponseMessage.ok(huntings, "Success");
        }
    }
}
