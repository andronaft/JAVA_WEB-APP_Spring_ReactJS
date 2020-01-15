package com.zuk.conference;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;




//    @Bean
//    DataSource dataSource() {
//        DataSource dataSource = DataSourceBuilder
//                .create(this.dataSourceProperties.getClassLoader())
//                .url(this.dataSourceProperties.getUrl())
//                .username(this.dataSourceProperties.getUsername())
//                .password(this.dataSourceProperties.getPassword())
//                .build();
//        return new DataSourceSpy(dataSource);
//    }
}