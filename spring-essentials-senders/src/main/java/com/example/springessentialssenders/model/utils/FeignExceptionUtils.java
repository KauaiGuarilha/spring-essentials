package com.example.springessentialssenders.model.utils;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.ErrorDTO;
import com.example.springessentialssenders.model.dto.ErrorsDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FeignExceptionUtils {

    public ErrorsDTO getErrorsDTO(FeignException ex) {

        ErrorsDTO resultado;
        try {
            log.error("An internal error occurred during a request with Feign", ex);
            log.error(ex.contentUTF8());
            resultado = errorsDTOGeneric();
            return resultado;
        } catch (Exception e) {
            log.error("An internal error occurred while trying to mount a Feign error return", ex);
            log.error(ex.contentUTF8());
            return errorsDTOGeneric();
        }
    }

    private ErrorsDTO errorsDTOGeneric() {
        List<ErrorDTO> errors = new ArrayList<>();
        errors.add(
                ErrorDTO.builder()
                        .code(EValidation.NOT_IDENTIFIED.getCode())
                        .message(EValidation.NOT_IDENTIFIED.getDescription())
                        .build());

        return ErrorsDTO.builder().errors(errors).build();
    }
}
