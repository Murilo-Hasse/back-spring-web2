package com.vagas.vagas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define o esquema de segurança (Security Scheme) para autenticação Bearer (JWT)
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // Adiciona o botão "Authorize" globalmente para todos os endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                // Adiciona informações gerais sobre a sua API
                .info(new Info()
                        .title("API de Vagas")
                        .version("v1.0")
                        .description("Documentação da API para o sistema de gerenciamento de vagas.")
                );
    }
}