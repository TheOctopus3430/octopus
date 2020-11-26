package octopus.util;


import java.sql.Connection;

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


        return null;
    }


}
