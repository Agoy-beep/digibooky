package com.testingtigers.domain.repositories;

import com.testingtigers.domain.users.Member;

import java.util.Map;

public class MemberRepository {

    private final Map<String, Member> members;

    public MemberRepository(Map<String, Member> members) {
        this.members = members;
    }

    public Member addMember(Member memberToAdd) {
        members.put(memberToAdd.getId(), memberToAdd);
        return memberToAdd;
    }
}
