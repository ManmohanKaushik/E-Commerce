package com.bikkadit.electronicstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayDeque;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        return docket;
//	     http://localhost:1972/swagger-ui/index.html
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "E-Commerce electronic store Backend: APIS ",
                "This is backend project created by Manmohan Kaushik",
                "1.0.0v",
                "https://bikkadit.com",
                new Contact("Manmohan Kaushik", "https://bikkadit.com", "manmohankaushik76035@gmail.com"),
                "License of APIS",
                "https://bikkadit.com",
                new ArrayDeque<>()
        );
        return apiInfo;
    }
}
