package com.example.springessentialssenders.config.feign.decoders;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.ErrorDTO;
import com.example.springessentialssenders.model.dto.ErrorsDTO;
import com.example.springessentialssenders.model.exceptions.FeignDecoderException;
import com.example.springessentialssenders.model.parser.interfaces.ValidationParser;
import com.example.springessentialssenders.model.utils.EssentialsObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class ErrorDecoderAbstract implements ErrorDecoder {

    private ValidationParser validationParser;

    public ErrorDecoderAbstract(ValidationParser validationParser) {
        this.validationParser = validationParser;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorsDTO exceptionDTO = new ErrorsDTO();

        try {
            exceptionDTO =
                    EssentialsObjectMapper.objectMapper()
                            .readValue(response.body().asInputStream(), ErrorsDTO.class);
        } catch (Exception e) {
            log.error(
                    "An internal error occurred during a request with the Feign for class validator type "
                            + validationParser.getClass().getName()
                            + ".",
                    e);

            List<ErrorDTO> erros = new ArrayList<>();
            erros.add(
                    ErrorDTO.builder()
                            .code(EValidation.NOT_IDENTIFIED.getCode())
                            .message(EValidation.NOT_IDENTIFIED.getDescription())
                            .build());
            exceptionDTO.setErrors(erros);
        }

        convertCodesValidation(exceptionDTO);
        return new FeignDecoderException(exceptionDTO);
    }

    private void convertCodesValidation(ErrorsDTO result) {
        result
                .getErrors()
                .forEach(
                        erro -> {
                            erro.setCode(validationParser.parseFromFeign(erro.getCode()));
                        });
    }
}
