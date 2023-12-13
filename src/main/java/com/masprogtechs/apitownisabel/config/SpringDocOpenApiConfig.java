package com.masprogtechs.apitownisabel.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(new Info()
                        .title("REST API - Sistema para gerenciar pagamento de quotas - Vila Isabel")
                        .version("v1")
                        .description("Api RestFull para para gerenciar pagamento de quotas - Vila Isabel")
                        .termsOfService("https://about-mauro.netlify.app/")
                        .license(
                                new License()
                                        .name("GNU GENERAL PUBLIC LICENSE")
                                        .url("https://www.gnu.org/licenses/gpl-3.0.pt-br.html")
                        )
                        .contact(new Contact().name("Mauro Manuel").url("https://www.linkedin.com/in/mauro-manuel-522947b2/"))
                );
    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .description("Insira um Bearer token valido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
