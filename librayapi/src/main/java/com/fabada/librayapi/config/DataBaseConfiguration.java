package com.fabada.librayapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//
//        ds.setUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//        return ds;
//    }
//
    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();

        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        System.out.println(driver);

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //maximo de conexão liberada
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library");
        config.setMaxLifetime(6000000); //600mil ms (10 minutos)
        config.setConnectionTimeout(1000000); //timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de test

        return new HikariDataSource(config);
    }


}
