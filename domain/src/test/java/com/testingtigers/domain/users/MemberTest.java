package com.testingtigers.domain.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MemberTest {

    @Test
    void checkEmail_GivenAValidEmailAddressIfCheckedDReturnsTrue() {
        //GIVEN
        String email = "hallo@gmail.com";
        //WHEN
        //THEN
        Assertions.assertThat(Member.isEmailValid(email)).isTrue();
    }

    @Test
    void checkEmail_GivenAnInvalidEmailAddressIfCheckedDReturnsFalse() {
        //GIVEN
        String email = "hallogmail.com";
        //WHEN
        //THEN
        Assertions.assertThat(Member.isEmailValid(email)).isFalse();
    }


}