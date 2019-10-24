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
    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberDto registerMember(CreateMemberDto memberToRegister) {

        Member registeredMember = new Member(memberToRegister.getINSS(), memberToRegister.getEmailAdress(), memberToRegister.getLastName(), memberToRegister.getCity());
        memberRepository.addMember(registeredMember);
        return memberMapper.convertMemberToDto(registeredMember);
    }
}


