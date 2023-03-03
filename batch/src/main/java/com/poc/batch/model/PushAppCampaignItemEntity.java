package com.poc.batch.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PushAppCampaignItemEntity {

    private Integer id;
    private String line;
    private String campaign;
    private String message;
    private Date validate;
    private Integer flag;
}
