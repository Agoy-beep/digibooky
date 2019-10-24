package com.testingtigers.api;

import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import io.restassured.RestAssured;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class BookControllerTest {

    private static int PORT = 9090;

    @Test
    void getAllBooks() {

        BookService bookService = new BookService();


            RestAssured
                .given()
                    .body(listOfCourses)
                    .accept(JSON)
                .when()
                    .port(PORT)
                    .get("/books")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .extract()
                        .as(listOfCourses);
    }
}