package com.testingtigers;

import com.testingtigers.domain.dtos.CreateMemberDto;
import com.testingtigers.domain.dtos.MemberDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void registerMember_GivenMemberDto_ThenItWorks() {
        //GIVEN
        CreateMemberDto createMemberDto = new CreateMemberDto("4564", "abc@gmail.com", "fredson", "bxl");
        //WHEN
        MemberDto memberDto =
                RestAssured
                        .given()
                        .body(createMemberDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(MemberDto.class);

                //THEN
        assertThat(memberDto.getCity()).isEqualTo("bxl");
    }
}