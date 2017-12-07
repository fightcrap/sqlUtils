import utils.SqlToBean;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {

            SqlToBean.run(SqlToBean.class.getResource("/").getPath()+"sqlInf.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
