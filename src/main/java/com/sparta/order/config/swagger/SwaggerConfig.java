package com.sparta.order.config.swagger;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Local Server");
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("Simple Order Service API")
                        .description("상품 관리 및 주문 처리 시스템 API 명세서")
                        .version("1.0.0"));
    }
}
