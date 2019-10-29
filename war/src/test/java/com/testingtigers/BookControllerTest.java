package com.testingtigers;

import com.testingtigers.domain.dtos.BookDto;
import com.testingtigers.domain.dtos.CreateBookDto;
import com.testingtigers.domain.exceptions.EmptyFields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;

   @Test
    public void addBook(){
        CreateBookDto createBookDto = new CreateBookDto()
                .setIsbn("ISBN")
                .setAuthorLastName("Pinker")
                .setTitle("Title");

        BookDto bookDto = RestAssured
                .given()
                .body(createBookDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookDto.class);

        assertThat(bookDto.getUniqueId()).isNotBlank();
        assertThat(bookDto.getIsbn()).isEqualTo(createBookDto.getIsbn());
    }

    @Test
    public void givenInputFieldIsEmptyThenThrowExeption(){
        CreateBookDto createBookDto = new CreateBookDto()
                .setIsbn("")
                .setAuthorLastName("Pinker")
                .setTitle("Title");

        RestAssured
                .given()
                .body(createBookDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }
}