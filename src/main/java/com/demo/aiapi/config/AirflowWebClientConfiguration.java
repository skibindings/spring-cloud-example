package com.demo.aiapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotBlank;

@Configuration
public class AirflowWebClientConfiguration {

    @Value("${airflow.username}")
    public String username;
    @Value("${airflow.password}")
    public String password;
    @Value("${host.airflow-webserver}")
    public String apiUrl;

    @Bean
    public WebClient airflowWebClient() {
        return  WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeaders(header -> header.setBasicAuth(
                        username,
                        password))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
