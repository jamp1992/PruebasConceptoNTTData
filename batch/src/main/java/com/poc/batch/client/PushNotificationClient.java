package com.poc.batch.client;


import com.poc.batch.dto.RequestPushNotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PushNotificationClient {

    @Value("${push.notification.service}")
    private String service;

    @Autowired
    private WebClient.Builder webClient;

    public Mono<Object> consumePushNotification(RequestPushNotificationDto request){

        return webClient.build().post()
                .uri(service)
                .header("Content-Type","application/json")
                .header("authorization","abc")
                .header("system","abc")
                .header("operation", "abc")
                .header("execId", "abcd1234-ab12-ab12-ab12-abcdef123456")
                .header("timestamp", "2014-01-31T09:30:47.233+01:00")
                .header("msgType", "sd")
                .header("varArg", "var")
                .body(Mono.just(request), RequestPushNotificationDto.class)
                .retrieve()
                .bodyToMono(Object.class);
    }
}
