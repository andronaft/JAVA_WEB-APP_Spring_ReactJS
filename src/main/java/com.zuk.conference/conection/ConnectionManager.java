package com.zuk.conference.conection;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class    ConnectionManager {

//    DataSourceProperties dataSourceProperties;
//    DataSource ds;



    public ConnectionManager() {

      /*  try {

            DataSource ds = DataSourceBuilder
                    .create(this.dataSourceProperties.getClassLoader())
                    .url(this.dataSourceProperties.getUrl())
                    .username(this.dataSourceProperties.getUsername())
                    .password(this.dataSourceProperties.getPassword())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test","sa","");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}