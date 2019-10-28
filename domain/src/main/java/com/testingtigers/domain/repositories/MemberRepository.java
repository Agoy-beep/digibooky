package com.testingtigers.domain.repositories;

import com.testingtigers.domain.exceptions.MemberNotFound;
import com.testingtigers.domain.users.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class MemberRepository {

    private final Map<String, Member> members;

    public MemberRepository() {

        this.members = new HashMap<>();

        // made by Danny for test purpose :
        Member memberToAdd = new Member("666", "jesus@heaven.hell", "christ","bethlehem");
        members.put(memberToAdd.getId(), memberToAdd);
    }

    public Member addMember(Member memberToAdd) {
        members.put(memberToAdd.getId(), memberToAdd);
        return memberToAdd;
    }

    public Collection<Member> getAll() {
        return members.values();
    }

    public Member getMemberByID(String memberID) {
        if (! members.containsKey(memberID)) {
            throw new MemberNotFound(HttpStatus.BAD_REQUEST,  "Member not found");   }
        return members.get(memberID);
    }
}
