package org.filter.config;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, Class<?>> TYPE_METRIC_MAP = new HashMap<>();
    static {
        TYPE_METRIC_MAP.put("AMOUNT", Number.class);
        TYPE_METRIC_MAP.put("TITLE", String.class);
        TYPE_METRIC_MAP.put("DATE", LocalDate.class);
    }

}
