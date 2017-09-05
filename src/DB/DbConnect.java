package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jiahuiyu on 2017/9/2.
 */
public class DbConnect {
    public static Connection getconn()
    {
        Connection conn = null;

        String user   = "jiahuiyu";
        String passwd = "abcdefgjhy123321";
        String url = "jdbc:mysql:localhost:3306/iqiyi_recource";

        //已加载完驱动
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,passwd);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return conn;
    }
}
