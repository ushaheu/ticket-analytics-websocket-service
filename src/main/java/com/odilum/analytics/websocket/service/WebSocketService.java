package com.arca.analytics.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketService {

    @Value(value = "${activemq.analytics.queue}")
    private String analyticsQueue;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @ServiceActivator(inputChannel = "greeting")
    public void pushAnalyticToWebSocket(Message<byte[]> message) {
        System.out.println("The message "+message);
        try {
            byte[] analyticsData = message.getPayload();
            String jsonResult = new String(analyticsData);
            System.out.println("The message received is " + jsonResult);
            simpMessagingTemplate.convertAndSend(analyticsQueue, jsonResult);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
