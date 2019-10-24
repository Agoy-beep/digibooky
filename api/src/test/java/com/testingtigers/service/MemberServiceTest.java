package com.testingtigers.service;

import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import com.testingtigers.domain.repositories.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Test
    void givenValidCreateDtoEverytingWorks() {
        //GIVEN
        MemberService memberService = new MemberService(new MemberRepository());
        CreateMemberDto createDto = new CreateMemberDto();
        createDto.setINSS("455");
        createDto.setEmailAdress("hallo@gmail.com");
        createDto.setFirstName("ddd");
        createDto.setLastName("Thaha");
        createDto.setCity("sad");
        createDto.setPostalCode("asd");
        createDto.setStreetName("asd");
        createDto.setStreetNumber("asd");
        MemberDto newMember = memberService.registerMember(createDto);
        //WHEN

        //THEN
        Assertions.assertThat(newMember.getPostalCode()).isEqualTo("asd");
    }

    @Test
    void givenEmptyCreateDtoThenError() {
        //GIVEN
        MemberService memberService = new MemberService(new MemberRepository());
        CreateMemberDto createDto = new CreateMemberDto();
        createDto.setINSS("");
        createDto.setEmailAdress("");
        createDto.setFirstName("");
        createDto.setLastName("");
        createDto.setCity("");
        createDto.setPostalCode("");
        createDto.setStreetName("");
        createDto.setStreetNumber("");

        Assertions.assertThatIllegalArgumentException();
    }
}