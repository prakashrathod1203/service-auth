package om.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import om.auth.constant.SwaggerConstants;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Service Auth").version("1.0")
                                                .description("Service Auth API(s)"))
                                .tags(List.of(new Tag().name(SwaggerConstants.ROLE_SCOPE_TAG)
                                                .description("Operations related to Role Scope"),
                                                new Tag().name(SwaggerConstants.ROLE_TAG)
                                                                .description("Operations related to Role"),
                                                new Tag().name(SwaggerConstants.TILE_TAG)
                                                                .description("Operations related to Tile"),
                                                new Tag().name(SwaggerConstants.USER_TAG)
                                                                .description("Operations related to User"))

                                );
        }
}
