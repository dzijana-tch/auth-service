package com.charniuk.authservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  @Value("${notification-service.api-key}")
  private String apiKey;

  @Bean
  public RequestInterceptor apiKeyInterceptor() {
    return template -> template.header("X-API-Key", apiKey);
  }
}