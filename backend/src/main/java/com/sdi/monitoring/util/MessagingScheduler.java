package com.sdi.monitoring.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.sdi.monitoring.model.oracle.dto.SocketDTO;

public class MessagingScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MessagingScheduler.class);
    
    private SimpMessagingTemplate messagingTemplate; 

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(cron="*/5 * * * * *")
    public void checkNotice(){
        try{
        	System.out.println("test");
        	SocketDTO socketVO = new SocketDTO("SERVER", "TEST");
            messagingTemplate.setMessageConverter(new StringMessageConverter());
            messagingTemplate.convertAndSend("/sendData/schedulerM", socketVO);
            System.out.println("test");
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }
}
