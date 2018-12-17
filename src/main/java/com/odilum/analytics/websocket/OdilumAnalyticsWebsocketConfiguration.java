package com.arca.analytics.websocket;

import com.arca.analytics.websocket.kafka.interfaces.AnalyticsChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@EnableBinding(AnalyticsChannel.class)
public class ArcaAnalyticsWebsocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Value(value = "${stomp.endpoint}")
    private String stompEndpoint;

    @Value(value = "${stomp.destination.prefix}")
    private String stompDestinationPrefix;

    @Value(value = "${activemq.broker.ip}")
    private String brokerRelayHost;

    @Value(value = "${activemq.broker.port}")
    private int brokerRelayPort;

    @Value(value = "${broker.heartbeat.interval}")
    private long heartBeatInterval;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableStompBrokerRelay(stompDestinationPrefix)
                .setRelayHost(brokerRelayHost)
                .setRelayPort(brokerRelayPort)
                .setSystemHeartbeatReceiveInterval(heartBeatInterval);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(stompEndpoint).withSockJS();
    }

}
