package octopus.util;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    //数据库配置

    private static final String DRIVER = "";
    private static final String URL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    //定义一个用于放置数据库连接的局部线程变量
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    //获取连接
    public static Connection getConnection() {
        Connection connection = connContainer.get();

        try {
            if (connection == null) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connContainer.set(connection);
        }
        return connection;
    }


}
