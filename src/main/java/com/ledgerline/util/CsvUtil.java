package com.ledgerline.util;

import java.util.List;
import java.util.Map;

public final class CsvUtil {
    private CsvUtil() {
    }

    public static String write(List<Map<String, Object>> rows, String header) {
        StringBuilder csv = new StringBuilder(header).append('\n');
        for (Map<String, Object> row : rows) {
            row.values().forEach(value -> csv.append(escape(value)).append(','));
            if (!row.isEmpty()) {
                csv.setLength(csv.length() - 1);
            }
            csv.append('\n');
        }
        return csv.toString();
    }

    private static String escape(Object value) {
        String text = String.valueOf(value);
        return '"' + text.replace("\"", "\"\"") + '"';
    }
}
