package com.serdyukov.atmservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApiTool() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(getApiInfo())
                .select()
                .paths(getPathsSelector())
                .apis(RequestHandlerSelectors.basePackage("com.serdyukov.atmservice.controller"))
                .build();
        docket.protocols(Collections.singleton("http"));
        return docket;
    }

    private Predicate<String> getPathsSelector() {
        return not(PathSelectors.regex("/error"));
    }

    private Predicate<RequestHandler> filterSpringBootEndpoints() {
        return Predicate.not(RequestHandlerSelectors.basePackage("org.springframework.boot"));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "CodeLions app",
                "CodeLions interview app",
                "1.0",
                "",
                "Serdyukov Aleksey",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

}
