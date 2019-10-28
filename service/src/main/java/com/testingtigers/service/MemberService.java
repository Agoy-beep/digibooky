package com.testingtigers.service;

import com.testingtigers.domain.dtos.CreateAdminDto;
import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.dtos.MemberMapper;
import com.testingtigers.domain.repositories.AdminRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Admin;
import com.testingtigers.domain.users.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberService {
    private MemberRepository memberRepository;
    private MemberMapper memberMapper;
    private AdminRepository adminRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, AdminRepository adminRepository) {
        this.memberRepository = memberRepository;
        this.adminRepository = adminRepository;
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

    public List<MemberDto> viewAllMembers() {
        List<MemberDto> allMembers = new ArrayList<>();
        memberRepository.getAll().forEach(member -> allMembers.add(memberMapper.convertMemberToDtoWithoutInss(member)));
        return allMembers;
    }

    public void registerAdmin(CreateAdminDto adminToCreate) {
        adminRepository.adAdmin(new Admin(adminToCreate.getLastName(), adminToCreate.getFirstName(), adminToCreate.getEmail()));
    }
}



