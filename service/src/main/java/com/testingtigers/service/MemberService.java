package com.testingtigers.service;

import com.testingtigers.domain.dtos.CreateAdminOrLibrarianDto;
import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.dtos.MemberMapper;
import com.testingtigers.domain.repositories.AdminRepository;
import com.testingtigers.domain.repositories.LibrarianRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Admin;
import com.testingtigers.domain.users.Librarian;
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
    private LibrarianRepository librarianRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, AdminRepository adminRepository, LibrarianRepository librarianRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.adminRepository = adminRepository;
        this.librarianRepository = librarianRepository;
        this.memberMapper = memberMapper;
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

    public void registerAdmin(CreateAdminOrLibrarianDto adminToCreate) {
        adminRepository.addAdmin(new Admin(adminToCreate.getLastName(), adminToCreate.getFirstName(), adminToCreate.getEmail()));
    }

    public void registerLibrarian(CreateAdminOrLibrarianDto librarianToCreate) {
        librarianRepository.addLibrarian(new Librarian(librarianToCreate.getLastName(), librarianToCreate.getFirstName(), librarianToCreate.getEmail()));
    }
}



