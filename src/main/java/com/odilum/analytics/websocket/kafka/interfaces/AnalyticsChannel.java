package com.arca.analytics.websocket.kafka.interfaces;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AnalyticsChannel {

    @Input(value = "greeting")
    SubscribableChannel input();
}
