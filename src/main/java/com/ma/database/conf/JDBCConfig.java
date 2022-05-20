package com.ma.database.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JDBCConfig {

    @Getter
    private static String driver;
    @Getter
    private static String url;
    @Getter
    private static String username;
    @Getter
    private static String password;

    @Value("${database.driver}")
    public void setDriver(String driver) {
        JDBCConfig.driver = driver;
    }

    @Value("${database.url}")
    public void setUrl(String url) {
        JDBCConfig.url = url;
    }

    @Value("${database.username}")
    public void setUsername(String username) {
        JDBCConfig.username = username;
    }


    @Value("${database.password}")
    public void setPassword(String password) {
        JDBCConfig.password = password;
    }


}
