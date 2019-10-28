package com.testingtigers.api.security;

import com.testingtigers.domain.repositories.AdminRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Authenticatable;
import com.testingtigers.domain.users.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserAuthenticationServiceTest {
    @Test
    void getMember_GivenEmailAndPassword_WhenGetAdminThatExistsAsAdmin_GetMember() {
        //GIVEN
        MemberRepository memberRepository = new MemberRepository();
        AdminRepository adminRepository = new AdminRepository();
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(memberRepository, adminRepository);
        //WHEN
        //THEN

        Authenticatable result = userAuthenticationService.getUser("admin@admin.com", "admin");
        Assertions.assertThat(result.getEmail().matches("admin@admin.com"));
    }

    @Test
    void getMember_GivenEmailAndPassword_WhenGetMemberWithWrongCredentials_GetNull() {
        //GIVEN
        MemberRepository memberRepository = new MemberRepository();
        AdminRepository adminRepository = new AdminRepository();
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(memberRepository, adminRepository);
        //WHEN
        //THEN

        Assertions.assertThat(userAuthenticationService.getUser("hallo", "123")).isNull();
    }

    @Test
    void getMember_GivenEmailAndPassword_WhenGetMemberThatExists_GetMember() {
        //GIVEN
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.addMember(new Member("jhkl", "hah@hah.com", "haha", "bxl"));

        AdminRepository adminRepository = new AdminRepository();
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(memberRepository, adminRepository);
        //WHEN
        //THEN

        Authenticatable result = userAuthenticationService.getUser("hah@hah.com", "member");
        Assertions.assertThat(result.getEmail().matches("hah@hah.com"));
    }
}
