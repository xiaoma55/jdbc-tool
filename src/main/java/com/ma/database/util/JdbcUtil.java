package com.ma.database.util;

import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.StrUtil;

import java.sql.*;

@Slf4j
public class JdbcUtil {

    public static Connection getJdbcConnection(String driver, String url, String username, String password) {
        System.out.println(StrUtil.format("url:{},username:{},password:{},driver:{}", url, username, password, driver));
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(StrUtil.format("get connection success,connection:{}", conn));
        } catch (Exception exception) {
            System.out.println(StrUtil.format("get connection failed,connection:{}", exception.getMessage()));
            exception.printStackTrace();
        }
        return conn;
    }

    public static Statement getJdbcStatement(Connection jdbcConnection) {
        Statement jdbcStatement = null;
        try {
            jdbcStatement = jdbcConnection.createStatement();
        } catch (SQLException exception) {
            log.error("create jdbc statement failed", exception);
            exception.printStackTrace();
        }
        System.out.println(StrUtil.format("create jdbc statement success,result:{}", jdbcStatement));
        return jdbcStatement;
    }

    public static void closeConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
                System.out.println(StrUtil.format("close jdbc connection success,connection:{}", connection));
            } catch (SQLException exception) {
                log.error("close jdbc connection failed", exception);
                exception.printStackTrace();
            }
        }
    }

    public static void closeConnection(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
                System.out.println(StrUtil.format("close jdbc statement success,statement:{}", statement));
            } catch (SQLException exception) {
                log.error("close jdbc statement failed", exception);
                exception.printStackTrace();
            }
        }
    }

    public static void closeConnection(ResultSet resultSet) {
        if (null != resultSet) {
            try {
                resultSet.close();
                System.out.println(StrUtil.format("close jdbc statement success,resultSet:{}", resultSet));
            } catch (SQLException exception) {
                log.error("close jdbc statement failed", exception);
                exception.printStackTrace();
            }
        }
    }

    public static void closeConnection(Connection connection, Statement statement) {
        closeConnection(statement);
        closeConnection(connection);
    }

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        closeConnection(resultSet);
        closeConnection(statement);
        closeConnection(connection);
    }
}
