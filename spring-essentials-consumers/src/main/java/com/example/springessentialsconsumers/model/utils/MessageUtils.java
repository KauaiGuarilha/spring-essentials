package com.example.springessentialsconsumers.model.utils;

import com.example.springessentialsconsumers.model.domain.interfaces.IEnumLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessageUtils {

    private static MessageSource MESSAGE_SOURCE;

    @Autowired
    private MessageSource resourceBundle;

    @PostConstruct
    public void init() {
        MESSAGE_SOURCE = resourceBundle;
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e) {
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum." + e.getClass().getSimpleName() + "." + ((Enum) e).name();
        return MESSAGE_SOURCE.getMessage(messageKey, null, locale);
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e, String... params) {
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum." + e.getClass().getSimpleName() + "." + ((Enum) e).name();
        return MESSAGE_SOURCE.getMessage(messageKey, params, locale);
    }
}
