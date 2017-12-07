package helper;

import java.util.HashMap;
import java.util.Map;

public class JavaPackageImportHelper {

    private static final Map<String, String> typeMap = new HashMap() {{
        put("Integer", "java.lang.Integer");
        put("Long", "java.lang.Long");
        put("Float", "java.lang.Float");
        put("Double", "java.lang.Double");
        put("BigDecimal", "java.math.BigDecimal");
        put("Date", "java.util.Date");
        put("String", "java.lang.String");
    }};


    public static String getClassName(String type) {
        return typeMap.get(type);

    }
}
