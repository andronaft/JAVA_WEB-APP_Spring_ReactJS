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
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;

        try {
            //connection = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-246-90-10.eu-west-1.compute.amazonaws.com:5432/d47rhj1fb2te3j?sslmode=require","quginjlexsklho","32adc7e0827e7fbe15f382b838850be5e5672f5e6849a803f9388489ec00a005");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}