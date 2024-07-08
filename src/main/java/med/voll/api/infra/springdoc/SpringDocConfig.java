package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig { //in order to use the token in swagger
   @Bean
   public OpenAPI customOpenAPI() {
      return new OpenAPI()
            .components(new Components()
                  .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                              .scheme("bearer").bearerFormat("JWT")))
            .info(new Info()
                  .title("Clinic API using postgres DB")
                  .description("API Rest for a Clinic that consists of" +
                        " CRUD functionalities for physicians, patients and appointments"));
   }

   @Bean
   public Void message() {
      System.out.println("bearer is working");
      return null;
   }
}
