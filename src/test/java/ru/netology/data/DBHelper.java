package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void runSQL() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getDebitOrderStatus() {
        runSQL();
        var orderStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, orderStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditOrderStatus() {
        runSQL();
        var orderStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, orderStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        runSQL();
        runner.execute(conn, "DELETE FROM credit_request_entity");
        runner.execute(conn, "DELETE FROM order_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
    }
}
