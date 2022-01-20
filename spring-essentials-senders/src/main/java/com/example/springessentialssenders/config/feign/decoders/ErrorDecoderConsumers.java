package com.example.springessentialssenders.config.feign.decoders;

import com.example.springessentialssenders.model.parser.validation.ValidationUtilsParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorDecoderConsumers extends ErrorDecoderAbstract {

    public ErrorDecoderConsumers() {
        super(new ValidationUtilsParser());
    }
}
