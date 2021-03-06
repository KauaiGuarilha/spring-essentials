package com.example.springessentialsconsumers.model.queue.consumers;

import com.example.springessentialsconsumers.model.parser.StudentParser;
import com.example.springessentialsconsumers.model.dto.StudentDTO;
import com.example.springessentialsconsumers.model.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentConsumerMessage {

    @Autowired private StudentParser parser;
    @Autowired private StudentRepository repository;

    @RabbitListener(queues = {"${spring.rabbitmq.student.queue}"})
    public void receive(@Payload StudentDTO dto){
        log.debug("Receiving queue of registered students.");
        repository.save(parser.toStudent(dto));
    }
}
