package com.udemy.primeiroprojetospringbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropsConfig {
  // Clsse para recuperar dados sensíveis de um arquivo para o properties
  @Bean
  public PropertySourcesPlaceholderConfigurer config() {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    configurer.setLocation(new FileSystemResource("/etc/config/primeirojobspringbatch/application.properties"));
    return configurer;
  }
}
