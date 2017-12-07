package utils;

import helper.JavaPackageImportHelper;
import helper.SqlTypeTranformerJavaTypeHelper;

import java.io.*;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateJavaFile {

    private final static String IMPORT_TEMPLATE = "import %s;\n";
    private final static String CLASS_TEMPLATE = "public class %s {\n";
    private final static String TABLE_NAME_TEMPLATE = "@Table(name=\"`%s`\")\n";
    private final static String COLUMN_TEMPLATE = "@Column(name=\"%s\")\n";
    private final static String ATTOTATION_IMPORT = "import javax.persistence.Column;\n" + "import javax.persistence.Table;\n";
    private final static String LOMBOK_ATTOTATION = "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "@Builder\n";

    /**
     * 创建class类的主体
     */
    public static void createClassGateWay(List<ResultSetMetaData> metaDatas, String classPath) throws SQLException, IOException {
        StringBuilder content = null;
        Set<String> javaType = null;
        for (ResultSetMetaData resultSetMetaData : metaDatas) {
            content = new StringBuilder();
            int count = resultSetMetaData.getColumnCount();
            javaType = new HashSet<>();
            for (int i = 1; i <= count; i++) {

                String name = resultSetMetaData.getColumnName(i);

                //添加column注解
                if (i == 1) {
                    content.append("@Id\n");
                }
                content.append(String.format(COLUMN_TEMPLATE, name));

                //设置属性名称
                StringBuilder stringBuilder = new StringBuilder("private ");
                String type = SqlTypeTranformerJavaTypeHelper.getJavaType(resultSetMetaData.getColumnTypeName(i));
                javaType.add(type);
                stringBuilder.append(type + " ");

                String propertyName = getPropertyName(name, false);
                stringBuilder.append(propertyName);

                stringBuilder.append(";\n\n\n");
                content.append(stringBuilder.toString());
            }

            String tableName = resultSetMetaData.getTableName(count);

            createClassFile(tableName, getPropertyName(tableName, true), content.toString(), classPath, javaType);

            System.out.println(content.toString());
        }
    }

    /**
     * 构建对应的java类
     *
     * @param className
     * @param classContent
     */
    private static void createClassFile(String baseTableName, String className, String classContent, String classPath, Set<String> javaType) throws IOException {
        if (!classPath.endsWith("/"))
            classPath = classPath + "/";
        File file = new File(classPath + className + ".java");
        file.createNewFile();
        BufferedOutputStream bufferedOutputStream = null;
        try {
            //构建表的头部和完整的class格式
            StringBuilder stringBuilder = createClassBody(baseTableName, className, javaType, classContent);

            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(stringBuilder.toString().getBytes());
        } finally {
            if (bufferedOutputStream != null)
                bufferedOutputStream.close();
        }
    }

    private static void createClassContent() {

    }

    /**
     * 构建class内容
     *
     * @param baseTableName
     * @param className
     * @param javaType
     * @param content
     * @return
     */
    private static StringBuilder createClassBody(String baseTableName, String className, Set<String> javaType, String content) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String type : javaType) {
            if ("byte[]".equals(type))
                continue;
            stringBuilder.append(String.format(IMPORT_TEMPLATE, JavaPackageImportHelper.getClassName(type)));
        }
        stringBuilder.append(ATTOTATION_IMPORT);
        stringBuilder.append("\n\n" + String.format(TABLE_NAME_TEMPLATE, baseTableName));
        stringBuilder.append(LOMBOK_ATTOTATION);
        stringBuilder.append(String.format(CLASS_TEMPLATE, className));
        stringBuilder.append(content);
        stringBuilder.append("\n}");
        return stringBuilder;

    }


    /**
     * 驼峰命名法转化
     *
     * @param name
     * @return
     */
    private static String getPropertyName(String name, Boolean isClassName) {

        String[] s = name.split("_");
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = (isClassName ? 0 : 1); j < s.length; j++) {
            char c = Character.toUpperCase(s[j].charAt(0));
            stringBuilder.append(c + s[j].substring(1));
        }
        if (!isClassName)
            stringBuilder.insert(0, s[0]);
        return stringBuilder.toString();
    }


}
