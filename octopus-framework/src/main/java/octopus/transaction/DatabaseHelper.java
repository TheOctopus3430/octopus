package octopus.transaction;

import java.sql.Connection;

import static octopus.util.DBUtil.*;

public class DatabaseHelper {
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();


    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                connContainer.set(connection);
            }
        }
    }

    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                connContainer.remove();
            }
        }
    }


    public static void  rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

}
