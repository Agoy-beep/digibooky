package com.testingtigers.service;

import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.repositories.AdminRepository;
import com.testingtigers.domain.repositories.LibrarianRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    @Test
    void givenValidCreateDtoEverytingWorks() {
        //GIVEN
        MemberService memberService = new MemberService(new MemberRepository(), new AdminRepository(), new LibrarianRepository());
        CreateMemberDto createDto = new CreateMemberDto();
        createDto.setInss("455");
        createDto.setEmailAddress("hallo@gmail.com");
        createDto.setFirstName("ddd");
        createDto.setLastName("Thaha");
        createDto.setCity("sad");
        createDto.setPostalCode("asd");
        createDto.setStreetName("asd");
        createDto.setStreetNumber("asd");
        MemberDto newMember = memberService.registerMember(createDto);
        //WHEN

        //THEN
        Assertions.assertThat(newMember.getINSS()).isEqualTo("455");
        Assertions.assertThat(newMember.getEmailAdress()).isEqualTo("hallo@gmail.com");
        Assertions.assertThat(newMember.getLastName()).isEqualTo("Thaha");
        Assertions.assertThat(newMember.getCity()).isEqualTo("sad");
    }

    @Test
    void givenEmptyCreateDtoThenError() {
        //GIVEN
        MemberService memberService = new MemberService(new MemberRepository(), new AdminRepository(), new LibrarianRepository());
        CreateMemberDto createDto = new CreateMemberDto();
        createDto.setInss("");
        createDto.setEmailAddress("");
        createDto.setFirstName("");
        createDto.setLastName("");
        createDto.setCity("");
        createDto.setPostalCode("");
        createDto.setStreetName("");
        createDto.setStreetNumber("");

        Assertions.assertThatIllegalArgumentException();
    }
}