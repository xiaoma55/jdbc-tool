package com.ma.database.util;

import cn.hutool.core.util.StrUtil;
import com.ma.database.conf.JDBCConfig;
import dnl.utils.text.table.TextTable;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class SqlUtil {

    private static String modeNumber = "2";

    private static String driver = JDBCConfig.getDriver();
    private static String url = JDBCConfig.getUrl();
    private static String username = JDBCConfig.getUsername();
    private static String password = JDBCConfig.getPassword();


    public static void executeSql() {
        while (true) {
            printModeOrSqlMessage();
            Scanner scan = new Scanner(System.in);
            String sqlOrModeNumber = scan.nextLine();
            if (StrUtil.isNotBlank(sqlOrModeNumber)){
                switch (sqlOrModeNumber) {
                    case "1":
                        testConnection();
                        break;
                    case "2":
                        modeNumber = "2";
                        break;
                    case "3":
                        modeNumber = "3";
                        break;
                    default:
                        executeSqlWithMode(sqlOrModeNumber);
                }
            }
        }
    }

    private static void executeSqlWithMode(String sql) {
        switch (modeNumber) {
            case "2":
                executeQuerySql(sql);
                break;
            case "3":
                executeUpdateSql(sql);
                break;
            default:
                System.out.println("no possible");
        }
    }

    private static void testConnection() {
        System.out.println(">");
        System.out.println("正在快马加鞭的测试连接:请稍等......");
        Connection jdbcConnection = JdbcUtil.getJdbcConnection(driver, url, username, password);
        if (null == jdbcConnection) {
            System.out.println("测试连接失败");
            return;
        }
        System.out.println("测试连接成功");
    }

    private static void executeQuerySql(String sql) {
        try {
            Connection jdbcConnection = JdbcUtil.getJdbcConnection(driver, url, username, password);
            Statement jdbcStatement = JdbcUtil.getJdbcStatement(jdbcConnection);
            executeQuerySqlDetail(sql, jdbcConnection, jdbcStatement);
        } catch (Exception e) {
            log.error("执行sql失败：", e);
        }
    }

    private static void executeUpdateSql(String sql) {
        try {
            Connection jdbcConnection = JdbcUtil.getJdbcConnection(driver, url, username, password);
            Statement jdbcStatement = JdbcUtil.getJdbcStatement(jdbcConnection);
            executeUpdateSqlDetail(sql, jdbcConnection, jdbcStatement);
        } catch (Exception e) {
            log.error("执行sql失败：", e);
        }
    }

    private static void executeQuerySqlDetail(String sql, Connection jdbcConnection, Statement jdbcStatement) throws Exception {
        List<String> columnList = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<>();
        ResultSet res = null;
        try {
            executeQuerySqlDetailResult(sql, jdbcStatement, res, columnList, dataList);
        } finally {
            JdbcUtil.closeConnection(jdbcConnection, jdbcStatement, res);
        }
        System.out.println("执行sql成功，结果为：");
        printTextTable(columnList, dataList);
    }

    private static void executeUpdateSqlDetail(String sql, Connection jdbcConnection, Statement jdbcStatement) throws Exception {
        Integer result = null;
        try {
            result = jdbcStatement.executeUpdate(sql);
        } finally {
            JdbcUtil.closeConnection(jdbcConnection, jdbcStatement);
        }
        System.out.println(StrUtil.format("执行sql成功，结果为：{}", result));
    }

    private static void printTextTable(List<String> columnList, List<List<String>> dataList) {
        Object[][] dataListArray = dataList.stream().map(x -> x.stream().toArray()).toArray(Object[][]::new);
        TextTable tt = new TextTable(columnList.toArray(new String[columnList.size()]), dataListArray);
        tt.printTable();
    }

    private static void executeQuerySqlDetailResult(String sql, Statement jdbcStatement, ResultSet res, List<String> columnList, List<List<String>> dataList) throws Exception {
        int flag = 0;
        res = jdbcStatement.executeQuery(sql);
        while (res.next()) {
            ResultSetMetaData metaData = res.getMetaData();
            if (flag == 0) {
                flag++;
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnList.add(metaData.getColumnName(i));
                }
            }
            List<String> rowDataList = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                rowDataList.add(res.getString(i));
            }
            dataList.add(rowDataList);
        }
    }

    private static void printModeOrSqlMessage() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("+--------------------------------------------------------------------- +");
        System.out.println("|模式：1:测试连接，2:query类型sql，3:update类型sql");
        System.out.println(StrUtil.format("|当前模式：【{}】，切换其他模式请输入对应数字", modeNumber));
        System.out.println("|请输入您的模式编号或者sql语句 ");
        System.out.println("+--------------------------------------------------------------------- +");
        System.out.print("> ");
    }
}
