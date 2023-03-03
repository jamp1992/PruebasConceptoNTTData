package com.poc.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPushNotificationDto {

    private String msisdn;
    private String operation;
    private String payLoad;
}
