package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SqlToBean {
    private Connection connection;

    private List<ResultSetMetaData> metaDatas = new LinkedList<>();

    private String url;

    private String userName;

    private String password;

    private String driver;

    private String classPath;

    private ArrayList<String> tables;


    /**
     * 链接数据库
     */
    public void connect() {
        try {
            //加载驱动
            Class.forName(this.driver);
            //获取链接
            connection = DriverManager.getConnection(this.url, this.userName, this.password);
            Statement statement = connection.createStatement();

            //通过循环来获取对应tables的列的数据
            for (String table : tables) {
                String sql = "select * from " + table;
                ResultSet resultSet = statement.executeQuery(sql);
                metaDatas.add(resultSet.getMetaData());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    /**
     * 获取运行的基本配置
     *
     * @param fileName
     */
    public void getBaseInfo(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String s = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
                JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
                //获取相对应的参数
                this.userName = (String) jsonObject.get("userName");
                this.driver = (String) jsonObject.get("driver");
                this.password = (String) jsonObject.get("password");
                this.url = (String) jsonObject.get("url");
                this.classPath = (String) jsonObject.get("classPath");
                JSONArray jsonArray = jsonObject.getJSONArray("tables");
                String[] a = new String[]{};
                a=jsonArray.toArray(a);
                this.tables = new ArrayList<String>(Arrays.asList(a));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (
                FileNotFoundException e)

        {
            e.printStackTrace();
        }
    }

    public static void run(String fileName) throws IOException, SQLException {
        SqlToBean sqlToBean=new SqlToBean();
        sqlToBean.getBaseInfo(fileName);
        sqlToBean.connect();
        CreateJavaFile.createClassGateWay(sqlToBean.metaDatas,sqlToBean.classPath);
    }





}
