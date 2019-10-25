package com.testingtigers.domain.dtos;

import com.testingtigers.domain.users.Member;

public class MemberMapper {

    public MemberDto convertMemberToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setINSS(member.getINSS());
        memberDto.setId(member.getId());
        memberDto.setEmailAdress(member.getEmailAddress());
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());
        memberDto.setCity(member.getCity());
        memberDto.setPostalCode(member.getPostalCode());
        memberDto.setStreetName(member.getStreetName());
        memberDto.setStreetNumber(member.getStreetNumber());
        return memberDto;
    }
}
