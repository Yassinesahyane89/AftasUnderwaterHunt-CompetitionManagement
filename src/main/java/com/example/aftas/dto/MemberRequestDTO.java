package com.example.aftas.dto;

import com.example.aftas.enums.IdentityDocumentType;
import com.example.aftas.model.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record MemberRequestDTO(
        @NotNull(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Family name is required")
        @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
        String familyName,

        @NotNull(message = "Nationality is required")
        String nationality,

        IdentityDocumentType identityDocumentType

) {
    public Member toMember(){
        return Member.builder()
                .name(name)
                .familyName(familyName)
                .nationality(nationality)
                .identityDocumentType(identityDocumentType)
                .build();
    }
}
