package com.huseyn;

import java.util.List;
import java.util.Optional;

public interface MemberDAO {
    void insertMember(Member member);
    void updateMember(Member member);
    void deleteMember(String id);
    List<Member> getMembers();
    Optional<Member> findMemberById(String id);
}