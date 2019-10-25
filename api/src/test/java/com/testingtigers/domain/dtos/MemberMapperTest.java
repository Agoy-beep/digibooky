package com.testingtigers.domain.dtos;

import com.testingtigers.domain.users.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {
    @Test
    void givenMemberWhenConvertGetProperDto() {
        //GIVEN
        MemberMapper memberMapper = new MemberMapper();
        Member member = new Member("7514","hah@gmail.com", "hah","han");
        //WHEN
        MemberDto memberDto = memberMapper.convertMemberToDto(member);
        //THEN
        Assertions.assertThat(memberDto.getFirstName()).isEqualTo(member.getFirstName());
        Assertions.assertThat(memberDto.getPostalCode()).isEqualTo("null");
    }

    @Test
    void givenMemberWhenConvertWithoutINSSThenGetADtoWithoutINSS() {
        //GIVEN
        MemberMapper memberMapper = new MemberMapper();
        Member member = new Member("7514","hah@gmail.com", "hah","han");
        //WHEN
        MemberDto memberDto = memberMapper.convertMemberToDtoWithoutInss(member);

        //THEN
        Assertions.assertThat(memberDto.getFirstName()).isEqualTo(member.getFirstName());
        Assertions.assertThat(memberDto.getINSS()).isEqualTo("Hidden for privacy reasons.");
    }
}