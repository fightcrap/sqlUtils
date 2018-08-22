package helper;

import java.util.HashMap;
import java.util.Map;

public class SqlTypeTranformerJavaTypeHelper {

    private static   Map<String, String> type = new HashMap() {{
        put("BIGINT", "Long");
        put("FLOAT", "Float");
        put("DOUBLE", "Double");
        put("NUMERIC", "Long");
        put("DECIMAL", "BigDecimal");
        put("TINYINT", "Integer");
        put("INT", "Integer");
        put("SMALLINT", "Integer");
        put("MEDIUMINT", "Integer");
        put("INTEGER", "Integer");
        put("BIGINT", "Long");
        put("CHAR", "String");
        put("VARCHAR", "String");
        put("TEXT", "String");

        put("BLOB", "byte[]");
        put("VARBINARY", "byte[]");
        put("TINYBLOB", "byte[]");
        put("BINARY", "byte[]");
        put("MEDIUMBLOB", "byte[]");
        put("LONGBLOB", "byte[]");

        put("DATE", "Date");
        put("DATETIME", "Date");
        put("TIMESTAMP", "Date");
        put("TIME", "Date");
        put("YEAR", "Date");
    }};





    public static String getJavaType(String sqlType) {

        /**
         * 每一数据类型可能会带有修饰，比如UNSIGNED
         */
        return type.get(sqlType.split(" ")[0]);
    }


}
