package com.ma.database;

import com.ma.database.util.SqlUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcToolApplication.class, args);
        SqlUtil.executeSql();
    }
}
