package com.zuk.conference.conection;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class    ConnectionManager {

//    DataSourceProperties dataSourceProperties;
//    DataSource ds;
    FileInputStream fis;
    Properties property = new Properties();

    public ConnectionManager(){
        try {
            fis = new FileInputStream("src/main/resources/application.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Connection getConnection() {
      /*  try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        Connection connection = null;

        try {
            //connection = DriverManager.getConnection(dataSourceProperties.getUrl(),dataSourceProperties.getUsername(),dataSourceProperties.getPassword());
            connection = DriverManager.getConnection(property.getProperty("spring.datasource.url"),property.getProperty("spring.datasource.username"),property.getProperty("spring.datasource.password"));
            //connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-246-90-10.eu-west-1.compute.amazonaws.com:5432/d47rhj1fb2te3j?sslmode=require","quginjlexsklho","32adc7e0827e7fbe15f382b838850be5e5672f5e6849a803f9388489ec00a005");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}