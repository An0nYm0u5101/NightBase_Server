package com.nightbase.api.amqp.controller;

import com.nightbase.api.amqp.producer.Producer;
import com.nightbase.api.amqp.model.AMQPMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("amqp")
public class AMQPController {

    @Autowired
    Producer producer;

    @PostMapping("/send")
    public ResponseEntity<?> sendMsg(@RequestBody AMQPMessage message){
        producer.produceMsg(message.getMessage());
        System.out.println("Sent message: " + message.getMessage());
        return ResponseEntity.ok().build();
    }

}
