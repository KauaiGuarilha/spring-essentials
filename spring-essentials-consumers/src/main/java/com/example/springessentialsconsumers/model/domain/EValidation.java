package com.example.springessentialsconsumers.model.domain;

import com.example.springessentialsconsumers.model.domain.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidation implements IEnumLabel<EValidation> {

    USERNAME_ALREADY_USING(1),
    USER_NOT_FOUND_FOR_ID(2),
    UUID_NOT_FOUND(3),
    STUDENT_NOT_FOUND_FOR_ID(4),
    STUDENT_NOT_FOUND_FOR_NAME(5),
    USER_NOT_FOUND_FOR_USERNAME(6),
    PAGEABLE_NOT_FOUND(7),
    NOT_IDENTIFIED(-999);

    private final Integer code;

    EValidation(Integer code) {
        this.code = code;
    }

    public static EValidation valueOf(Integer code) {
        for (EValidation val : EValidation.values()) {
            if (val.code.equals(code)) return val;
        }
        return null;
    }
}
