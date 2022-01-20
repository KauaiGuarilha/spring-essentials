package com.example.springessentialssenders.model.parser.validation;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.parser.interfaces.ValidationParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ValidationUtilsParser implements ValidationParser {

    private final Map<Integer, Integer> mapCodes;

    {
        mapCodes =
                Map.ofEntries(
                        Map.entry(-999, EValidation.NOT_IDENTIFIED.getCode()));
    }

    public Integer parseFromFeign(Integer code) {
        if (mapCodes.containsKey(code)) return mapCodes.get(code);

        log.error(
                "The code: "
                        + code
                        + " returned by project Consumers"
                        + " is not a code recognized by the system.");
        return EValidation.NOT_IDENTIFIED.getCode();
    }
}
