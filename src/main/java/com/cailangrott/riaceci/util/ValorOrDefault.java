package com.cailangrott.riaceci.util;

public class ValorOrDefault {
    public static <T> T getValorOrDefault(T valorDefault, T value) {
        return value == null || (value instanceof String && ((String) value).isBlank()) ? valorDefault : value;
    }
}
