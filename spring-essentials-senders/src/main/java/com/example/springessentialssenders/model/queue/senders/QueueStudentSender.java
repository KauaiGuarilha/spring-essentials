package com.example.springessentialssenders.model.queue.senders;

import com.example.springessentialssenders.model.dto.StudentDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueStudentSender {

    @Autowired private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.student.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.student.routingkey}")
    private String routingKey;

    public void sendMessage(StudentDTO studentDTO){
        rabbitTemplate.convertAndSend(exchange, routingKey, studentDTO);
    }
}
