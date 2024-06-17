package com.example.spontecular.utility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class StringUtils {

    public String toUpperCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder builder = new StringBuilder();
        boolean capitalizeNext = true;

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    builder.append(Character.toUpperCase(ch));
                    capitalizeNext = false;
                } else {
                    builder.append(Character.toLowerCase(ch));
                }
            }
        }
        return builder.toString();
    }


    public String toLowerCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder builder = new StringBuilder();
        boolean capitalizeNext = false;

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    builder.append(Character.toUpperCase(ch));
                    capitalizeNext = false;
                } else {
                    if (builder.isEmpty()) {
                        builder.append(Character.toLowerCase(ch));
                    } else {
                        builder.append(ch);
                    }
                }
            }
        }
        return builder.toString();
    }
}
