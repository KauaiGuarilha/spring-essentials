package com.example.springessentials.model.domain;

import com.example.springessentials.model.domain.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidation implements IEnumLabel<EValidation> {

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
