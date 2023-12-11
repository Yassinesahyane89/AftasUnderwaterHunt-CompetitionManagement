package com.example.aftas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @NotNull(message = "Name cannot be null")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull(message = "Start time cannot be null")
    @Future(message = "Start time must be in the future")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "Start time cannot be null")
    @Future(message = "Start time must be in the future")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalDateTime endTime;

    @NotNull(message = "Location cannot be null")
    @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
    private String location;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than 0")
    private int amount;

    @OneToMany(mappedBy = "competition")
    private List<Ranking> ranking;

    @OneToMany(mappedBy = "competition")
    private List<Hunting> hunting;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
}
