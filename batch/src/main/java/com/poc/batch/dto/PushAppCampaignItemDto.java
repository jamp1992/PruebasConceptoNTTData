package com.poc.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class PushAppCampaignItemDto {

    private Integer id;
    private String line;
    private String campaign;
    private String message;
    private Date validate;
    private Integer flag;

    public PushAppCampaignItemDto() {
    }
}
