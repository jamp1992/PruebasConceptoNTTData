package com.poc.batch.config.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WebClientConfig {

    static final int MAX_T = 10;

    @Value("${push.notification.url}")
    private String url;

    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder().baseUrl(url);
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executeService(){
        return Executors.newFixedThreadPool(MAX_T);
    }
}
