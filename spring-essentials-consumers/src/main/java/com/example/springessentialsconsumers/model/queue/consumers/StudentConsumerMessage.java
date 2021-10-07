package com.example.springessentialsconsumers.model.queue.consumers;

import com.example.springessentialsconsumers.model.builder.StudentParser;
import com.example.springessentialsconsumers.model.dto.StudentDTO;
import com.example.springessentialsconsumers.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class StudentConsumerMessage {

    @Autowired private StudentParser parser;
    @Autowired private StudentRepository repository;

    //@RabbitListener(queues = {"${spring.rabbitmq.student.queue}"})
    public void receive(@Payload StudentDTO dto){
        repository.save(parser.toStudent(dto));
    }
}
