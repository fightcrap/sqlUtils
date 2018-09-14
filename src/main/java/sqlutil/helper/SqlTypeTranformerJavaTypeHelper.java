package sqlutil.helper;

import java.util.HashMap;
import java.util.Map;

public class SqlTypeTranformerJavaTypeHelper {

    private static final Map<String, String> TYPE = new HashMap<String, String>() {
        private static final long serialVersionUID = 8138341553250334160L;
        {
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

        return TYPE.get(sqlType.split(" ")[0]);
    }


}
