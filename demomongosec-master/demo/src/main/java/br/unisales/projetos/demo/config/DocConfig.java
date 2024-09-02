package br.unisales.projetos.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class DocConfig {

 /*
  * objeto gerenciado pelo Spring que adiciona informações à documentação
  */
 @Bean
 public OpenAPI customOpenAPI() {
  return new OpenAPI()
    .addSecurityItem(new SecurityRequirement().addList("Auth JWT"))
    .components(new Components()
      .addSecuritySchemes("Auth JWT",
        new SecurityScheme()
          .name("Auth JWT")
          .type(SecurityScheme.Type.HTTP)
          .scheme("Bearer")
          .bearerFormat("JWT")))
    .info(new Info()
      .title("Aplicação usando mongo")
      .version("1.0.0")
      .contact(new Contact().email("hebertraphalsky@hotmail.com").name("Hebert Raphalsky"))
      .description("<h1>Exemplo de REST API para projetos</h1><p>Código disponível no "
        + "<a href='https://github.com/HebertRaphalsky/Projeto-Aplicacao-Hibrida' target='_blank'>Github</a></p>"
        + "<p>Teste o login usando os seguintes <b>login - senha</b>:</p>"
        + "<p><ul><li>admin - 1234</li><li>usuario1 - 1234</li><li>usuario2 - 1234</li></ul></p>"));
 }

}
