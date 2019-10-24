package com.testingtigers.domain.repositories;

import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class MemberRepository {

    private final Map<String, Member> members;

    public MemberRepository() {
        this.members = new HashMap<>();
    }

    public Member addMember(Member memberToAdd) {
        members.put(memberToAdd.getId(), memberToAdd);
        return memberToAdd;
    }
}
