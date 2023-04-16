package in.om.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static in.om.security.JwtUtil.AUTHORIZATION;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Global API document configuration.
 */
@EnableWebMvc
@EnableSwagger2
@Component
public class SwaggerConfig {

    /**
     * Swagger UI configuration
     */
    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .operationsSorter(OperationsSorter.METHOD)
                .build();
    }

    /**
     * User authentication API's configurations.
     */
    @Bean
    public Docket authAPI(ServletContext servletContext) {
        return new Docket(SWAGGER_2).apiInfo(apiInfo()).groupName("Authentication")
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
                .select()
                .paths(PathSelectors.regex(".*/auth.*")).build();
    }

    /**
     * User API's configurations.
     */
    @Bean
    public Docket userAPI(ServletContext servletContext) {
        return new Docket(SWAGGER_2).apiInfo(apiInfo()).groupName("Users")
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
                .select()
                .paths(PathSelectors.regex(".*/user.*")).build();
    }

    /**
     * Here configure the application basic details.
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("OnsetMatrix", "", "onsetmatrix@gmail.com");
        return new ApiInfoBuilder().title("Central Auth").contact(contact).version("1.0").build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION, AUTHORIZATION, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return Collections.singletonList(new SecurityReference(AUTHORIZATION, authorizationScopes));
    }
}
