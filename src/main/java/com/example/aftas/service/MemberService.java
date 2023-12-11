package com.example.aftas.service;

import com.example.aftas.model.Member;

import java.util.List;

public interface MemberService {
    Member getMemberById(Long id);
    List<Member> getAllMembers();
    Member addMember(Member member);
    List<Member> findByNameOrMembershipNumberOrFamilyName(String searchTerm);
    Member updateMember(Member member, Long id);
    void deleteMember(Long id);
}
