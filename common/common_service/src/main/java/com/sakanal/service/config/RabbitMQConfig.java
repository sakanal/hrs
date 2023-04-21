package com.sakanal.service.config;

import com.sakanal.service.properties.MyCommonRabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitMQConfig {
    private RabbitTemplate rabbitTemplate;

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     * 1、服务收到消息就会回调
     * 1、spring.rabbitmq.publisher-confirms: true
     * 2、设置确认回调
     * 2、消息正确抵达队列就会进行回调
     * 1、spring.rabbitmq.publisher-returns: true
     * spring.rabbitmq.template.mandatory: true
     * 2、设置确认回调ReturnCallback
     * <p>
     * 3、消费端确认(保证每个消息都被正确消费，此时才可以broker删除这个消息)
     */
    // @PostConstruct  //MyRabbitConfig对象创建完成以后，执行这个方法
    public void initRabbitTemplate() {
        /*
          1、只要消息抵达Broker就ack=true
          correlationData：当前消息的唯一关联数据(这个是消息的唯一id)
          ack：消息是否成功收到
          cause：失败的原因
         */
        //设置确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm...correlationData[" + correlationData + "]==>ack:[" + ack + "]==>cause:[" + cause + "]");
        });
        /*
          只要消息没有投递给指定的队列，就触发这个失败回调
          message：投递失败的消息详细信息
          replyCode：回复的状态码
          replyText：回复的文本内容
          exchange：当时这个消息发给哪个交换机
          routingKey：当时这个消息用哪个路邮键
         */
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();

            System.out.println("Fail Message[" + message + "]==>replyCode[" + replyCode + "]" +
                    "==>replyText[" + replyText + "]==>exchange[" + exchange + "]==>routingKey[" + routingKey + "]");
        });

    }

    @Bean("imgDelayExchange")
    public TopicExchange imgDelayExchange() {
        return ExchangeBuilder.topicExchange(MyCommonRabbitMQProperties.IMG_OSS_DELAY_EXCHANGE).build();
    }

    @Bean("imgReleaseExchange")
    public TopicExchange imgReleaseExchange() {
        return ExchangeBuilder.topicExchange(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_EXCHANGE).build();
    }

    @Bean("imgDelayQueue")
    public Queue imgDelayQueue() {
        return QueueBuilder.durable(MyCommonRabbitMQProperties.IMG_OSS_DELAY_QUEUE)
                .build();
    }

    @Bean("imgReleaseQueue")
    public Queue imgReleaseQueue() {
        return QueueBuilder.durable(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_QUEUE)
                .deadLetterExchange(MyCommonRabbitMQProperties.IMG_OSS_DELAY_EXCHANGE)
                .deadLetterRoutingKey(MyCommonRabbitMQProperties.IMG_OSS_DELAY_ROUTING_KEY)
//                .ttl(1000 * 60 * 60)//一小时
                .ttl(1000 * 60)
                .build();
    }

    @Bean("orderReleaseQueue")
    public Queue orderReleaseQueue() {
        return QueueBuilder.durable(MyCommonRabbitMQProperties.ORDER_RELEASE_QUEUE)
                .deadLetterExchange(MyCommonRabbitMQProperties.IMG_OSS_DELAY_EXCHANGE)
                .deadLetterRoutingKey(MyCommonRabbitMQProperties.ORDER_DELAY_ROUTING_KEY)
//                .ttl(1000 * 60 * 30)// 三十分钟
                .ttl(1000 * 60)
                .build();
    }

    @Bean
    public Queue orderDelayQueue(){
        return QueueBuilder.durable(MyCommonRabbitMQProperties.ORDER_DELAY_QUEUE)
                .build();
    }

    @Bean
    public Binding queueReleaseBinding(TopicExchange imgReleaseExchange, Queue imgReleaseQueue) {
        return BindingBuilder
                .bind(imgReleaseQueue)
                .to(imgReleaseExchange)
                .with(MyCommonRabbitMQProperties.IMG_OSS_RELEASE_ROUTING_KEY);
    }

    @Bean
    public Binding queueDelayBing(TopicExchange imgDelayExchange, Queue imgDelayQueue) {
        return BindingBuilder
                .bind(imgDelayQueue)
                .to(imgDelayExchange)
                .with(MyCommonRabbitMQProperties.IMG_OSS_DELAY_ROUTING_KEY);
    }

    @Bean
    public Binding orderQueueReleaseBinging(TopicExchange imgReleaseExchange, Queue orderReleaseQueue){
        return BindingBuilder
                .bind(orderReleaseQueue)
                .to(imgReleaseExchange)
                .with(MyCommonRabbitMQProperties.ORDER_RELEASE_ROUTING_KEY);
    }

    @Bean
    public Binding orderQueueDelayBinging(TopicExchange imgDelayExchange,Queue orderDelayQueue){
        return BindingBuilder
                .bind(orderDelayQueue)
                .to(imgDelayExchange)
                .with(MyCommonRabbitMQProperties.ORDER_DELAY_ROUTING_KEY);
    }

}
