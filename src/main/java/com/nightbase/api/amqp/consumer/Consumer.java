package com.nightbase.api.amqp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    /*@RabbitListener(queues="${nightbase.rabbitmq.queue}")
    public void recievedMessage(String msg){
        System.out.println("Received Message: " + msg);
    }*/

}
