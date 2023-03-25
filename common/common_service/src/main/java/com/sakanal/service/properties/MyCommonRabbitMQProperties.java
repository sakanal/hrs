package com.sakanal.service.properties;

import io.lettuce.core.resource.Delay;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyCommonRabbitMQProperties {
    public static final String IMG_OSS_DELAY_EXCHANGE = "img_event_exchange";
    public static final String IMG_OSS_RELEASE_EXCHANGE = "img_release_exchange";


    public static final String IMG_OSS_DELAY_QUEUE="imgDelayQueue";
    public static final String IMG_OSS_RELEASE_QUEUE="imgReleaseQueue";

    public static final String IMG_OSS_DELAY_ROUTING_KEY = "img.delay.routingKey";
    public static final String IMG_OSS_RELEASE_ROUTING_KEY = "img.release.routingKey";
}
