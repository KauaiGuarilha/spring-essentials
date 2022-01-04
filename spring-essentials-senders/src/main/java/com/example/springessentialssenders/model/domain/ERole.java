package com.example.springessentialssenders.model.domain;

import com.example.springessentialssenders.model.domain.interfaces.IEnumLabel;

public enum ERole implements IEnumLabel<ERole> {
    USER(1),
    ADMIN(2);


    private final Integer code;

    ERole(Integer code) {
        this.code = code;
    }

    public Integer getCodeRole() {
        return this.code;
    }

    @Override
    public String toString() {
        return code.toString();
    }

    public static ERole valueOf(Integer code) {
        for (ERole cod : ERole.values()) {
            if (cod.code == code) return cod;
        }
        return null;
    }
}
