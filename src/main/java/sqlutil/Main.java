package sqlutil;

import sqlutil.utils.SqlToBean;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            SqlToBean.run(SqlToBean.class.getResource("/sqlInf-test.json").getPath());
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
