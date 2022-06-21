package com.example.springessentialssenders.model.domain;

import com.example.springessentialssenders.model.domain.interfaces.IEnumLabel;
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
    BLOB_STORAGE_NOT_FOUND(8),
    BLOB_DATABASE_NOT_FOUND(9),
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
