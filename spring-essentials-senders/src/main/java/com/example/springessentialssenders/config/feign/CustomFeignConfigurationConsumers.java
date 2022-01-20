package com.example.springessentialssenders.config.feign;

import com.example.springessentialssenders.config.feign.decoders.ErrorDecoderConsumers;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class CustomFeignConfigurationConsumers {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoderConsumers();
    }
}
