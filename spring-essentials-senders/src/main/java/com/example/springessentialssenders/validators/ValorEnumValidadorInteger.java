package com.example.springessentialssenders.validators;

import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ValorEnumValidadorInteger implements ConstraintValidator<ValueEnumInteger, Integer> {

    private Class classeEnum;

    @Override
    public void initialize(ValueEnumInteger annotation) {
        this.classeEnum = annotation.classeEnum();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return getEnumValues(classeEnum).stream()
                .anyMatch(
                        propriedadeEnum ->
                                value.equals((Integer.valueOf(propriedadeEnum.toString()))));
    }

    private <E extends Enum> List<E> getEnumValues(Class<E> enumClass)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = enumClass.getDeclaredField("$VALUES");
        f.setAccessible(true);
        Object o = f.get(null);
        return Arrays.asList((E[]) o);
    }
}
