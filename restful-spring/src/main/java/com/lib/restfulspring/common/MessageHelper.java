package com.lib.restfulspring.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageHelper {
    private static final String NOT_FOUND_ERROR = " not found in message source.";
    private final MessageSource messageSource;

    public String getMessage(String code, String[] params, Locale locale) {
        try {
            return messageSource.getMessage(code, params, locale);
        } catch (Exception e) {
            e.printStackTrace();
            return code + NOT_FOUND_ERROR;
        }
    }
}
