package cosmetics.BOGOShop;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
    private Info apiInfo(){
        return new Info()
                .title("Swagger 문서 제목")
                .description("Swagger 문서 설명")
                .version("1.0");
    }

//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("Swagger 문서 제목")
//                .description("Swagger 문서 설명")
//                .version("1.0")
//                .build();
//    }
}
