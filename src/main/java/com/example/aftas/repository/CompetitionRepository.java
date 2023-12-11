package com.example.aftas.repository;

import com.example.aftas.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition findByDate(Date date);
}
