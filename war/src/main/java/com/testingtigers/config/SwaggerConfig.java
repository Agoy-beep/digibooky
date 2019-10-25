package com.testingtigers.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket webApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .build()
                .apiInfo(webAppInfo());
    }

    private ApiInfo webAppInfo(){
        return new ApiInfo(
                "DIGIBOOKY DOCS",
                "A guide to the virtual book depository",
                "0.1",
                "Terms of Service",
                new Contact("TestingTigers", "www.TesTig.org", "testingtigers@TesTig.org"),
                "Creative Commons", "api@api.com", Collections.emptyList());

    }

}
