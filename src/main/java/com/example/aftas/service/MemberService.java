package com.example.aftas.service;

import com.example.aftas.model.Member;

public interface MemberService {
    Member getMemberById(Long id);
    Member addMember(Member member);
    Member searchMember(String name);
    Member updateMember(Member member, Long id);
    void deleteMember(Long id);
}
