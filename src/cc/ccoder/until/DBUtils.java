package cc.ccoder.until;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 18:51
 * Package   :  cc.ccoder.until
 * Describe  :  TODO
 */
public class DBUtils {

    //	private static final String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	private static final String url = "jdbc:sqlserver://localhost:1433;DataBaseName=squadStore";
//	private static final String user = "sa";
//	private static final String password = "123456";

    private static final String Driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/studystruts";
    private static final String user = "root";
    private static final String password = "123456";
    private static final String ssl = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    private static Connection connection = null;

    static {
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(url+ssl, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBUtils() {

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        if (getConnection() == null) {
            System.out.println("eror");
        } else {
            System.out.println("ok");
        }
    }

}
