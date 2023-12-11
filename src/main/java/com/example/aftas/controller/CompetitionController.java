package com.example.aftas.controller;

import com.example.aftas.handlers.response.ResponseMessage;
import com.example.aftas.model.Competition;
import com.example.aftas.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/competitions")
public class CompetitionController {

    private CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        if(competition == null) {
            return ResponseMessage.notFound("Competition not found");
        }else {
            return ResponseMessage.ok(competition, "Success");
        }
    }

    @PostMapping
    public ResponseEntity addCompetition(@RequestBody Competition competition) {
        Competition competition1 = competitionService.addCompetition(competition);
        if(competition1 == null) {
            return ResponseMessage.badRequest("Competition not created");
        }else {
            return ResponseMessage.created(competition1, "Competition created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCompetition(@RequestBody Competition competition, @PathVariable Long id) {
        Competition competition1 = competitionService.updateCompetition(competition, id);
        if(competition1 == null) {
            return ResponseMessage.badRequest("Competition not updated");
        }else {
            return ResponseMessage.created(competition1, "Competition updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }

}
