package com.testingtigers.service;

import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.dtos.MemberMapper;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberService {
    private MemberRepository memberRepository;
    private MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = new MemberMapper();
    }

    public MemberDto registerMember(CreateMemberDto memberToRegister) {

        Member registeredMember = new Member(memberToRegister.getInss(), memberToRegister.getEmailAddress(), memberToRegister.getLastName(), memberToRegister.getCity());
        registeredMember.setFirstName(memberToRegister.getFirstName());
        registeredMember.setPostalCode(memberToRegister.getPostalCode());
        registeredMember.setStreetName(memberToRegister.getStreetName());
        registeredMember.setStreetNumber(memberToRegister.getStreetNumber());
        memberRepository.addMember(registeredMember);
        return memberMapper.convertMemberToDto(registeredMember);
    }
}



