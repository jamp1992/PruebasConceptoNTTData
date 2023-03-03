package com.poc.batch.processor;

import com.poc.batch.client.PushNotificationClient;
import com.poc.batch.dto.PushAppCampaignItemDto;
import com.poc.batch.dto.RequestPushNotificationDto;
import com.poc.batch.model.PushAppCampaignItemEntity;
import com.poc.batch.util.PushAppCampaignTask;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;

public class PushAppCampaignItemProcessor implements ItemProcessor<PushAppCampaignItemDto, PushAppCampaignItemEntity> {

    @Autowired
    private PushNotificationClient pushNotificationClient;

    @Autowired
    private ExecutorService pool;

    @Override
    public PushAppCampaignItemEntity process(PushAppCampaignItemDto pushAppCampaignItemDto) throws Exception {

        Runnable r1 = new PushAppCampaignTask(pushNotificationClient, pushAppCampaignItemDto);
        pool.execute(r1);

        return PushAppCampaignItemEntity.builder()
                .id(pushAppCampaignItemDto.getId())
                .line(pushAppCampaignItemDto.getLine())
                .campaign(pushAppCampaignItemDto.getCampaign())
                .message(pushAppCampaignItemDto.getMessage())
                .validate(pushAppCampaignItemDto.getValidate())
                .flag(1)
                .build();
    }
}
