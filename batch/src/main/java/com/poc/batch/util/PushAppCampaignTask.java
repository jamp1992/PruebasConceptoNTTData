package com.poc.batch.util;


import com.poc.batch.client.PushNotificationClient;
import com.poc.batch.dto.PushAppCampaignItemDto;
import com.poc.batch.dto.RequestPushNotificationDto;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class PushAppCampaignTask implements Runnable{

    private PushAppCampaignItemDto pushAppCampaignItemDto;

    private PushNotificationClient pushNotificationClient;

    public PushAppCampaignTask(PushNotificationClient pushNotificationClient, PushAppCampaignItemDto pushAppCampaignEntity){
        this.pushAppCampaignItemDto = pushAppCampaignEntity;
        this.pushNotificationClient = pushNotificationClient;
    }

    @Override
    public void run() {
        try
        {
            RequestPushNotificationDto requestPushNotificationDTO = new RequestPushNotificationDto();
            requestPushNotificationDTO.setMsisdn(pushAppCampaignItemDto.getLine());
            requestPushNotificationDTO.setOperation(pushAppCampaignItemDto.getCampaign());
            requestPushNotificationDTO.setPayLoad(pushAppCampaignItemDto.getMessage());
            pushNotificationClient.consumePushNotification(requestPushNotificationDTO).subscribe();
            Thread.sleep(1000);
            Date d = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            log.info("Executing Time for number phone - "+
                    pushAppCampaignItemDto.getLine() +" = " +ft.format(d));
        }

        catch(InterruptedException e)
        {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
