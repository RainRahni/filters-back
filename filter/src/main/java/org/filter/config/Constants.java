package org.filter.config;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static final Map<String, Class<?>> TYPE_METRIC_MAP = new HashMap<>();
    static {
        TYPE_METRIC_MAP.put("AMOUNT", Number.class);
        TYPE_METRIC_MAP.put("TITLE", String.class);
        TYPE_METRIC_MAP.put("DATE", LocalDate.class);
    }
    public static final String NUMBER_PATTERN = "\\d+";
    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    public static final List<String> TYPES = List.of("AMOUNT", "TITLE", "DATE");
    public static final String INVALID_INPUT_MESSAGE = "Invalid input!";

}
