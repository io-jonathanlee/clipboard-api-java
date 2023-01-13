package io.jonathanlee.clipboardapijava.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
    corsConfiguration.setAllowedHeaders(List.of(
        "Origin",
        "Access-Control-Allow-Origin",
        "Content-Type",
        "Accepts",
        "Authorization",
        "Origin, Accept",
        "X-Requested-With",
        "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
    ));
    corsConfiguration.setExposedHeaders(List.of(
        "Origin", "Content-Type", "Accept", "Authorization",
        "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
    ));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    String[] allowedDomains = new String[]{
        "http://localhost:4200"
    };

    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedDomains);
      }
    };
  }

}
