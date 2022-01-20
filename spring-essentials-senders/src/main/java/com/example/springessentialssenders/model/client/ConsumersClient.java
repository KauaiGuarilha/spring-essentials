package com.example.springessentialssenders.model.client;

import com.example.springessentialssenders.config.feign.CustomFeignConfigurationConsumers;
import com.example.springessentialssenders.model.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        url = "${feign.client.url.consumers}",
        name = "${feign.client.name.consumers}",
        configuration = CustomFeignConfigurationConsumers.class)
public interface ConsumersClient {

    @RequestMapping(
            value = "${feign.client.url.consumers.student.findbyname}",
            method = RequestMethod.GET)
    List<Student> findConsumersByNameIgnoreCaseContaining(@PathVariable String name);
}
