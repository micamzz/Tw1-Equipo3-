package com.tallerwebi.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

  /*
  @Bean
  public DataSource dataSource2() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
    dataSource.setUrl("jdbc:hsqldb:mem:db_");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }


  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
    dataSource.setUrl("jdbc:mariadb://localhost:3306/tw1");
    dataSource.setUsername("root");
    dataSource.setPassword("1234");
    return dataSource;
  }

  */

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    String dbHost = System.getenv("DB_HOST");
    String dbPort = System.getenv("DB_PORT");
    String dbName = System.getenv("DB_NAME");
    String dbUser = System.getenv("DB_USER");
    String dbPassword = System.getenv("DB_PASSWORD");

    if (dbHost == null) dbHost = "localhost";
    if (dbPort == null) dbPort = "3306";
    if (dbName == null) dbName = "tw1";
    if (dbUser == null) dbUser = "root";
    if (dbPassword == null) dbPassword = "1234";

    String url = String.format("jdbc:mariadb://%s:%s/%s", dbHost, dbPort, dbName);

    dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
    dataSource.setUrl(url);
    dataSource.setUsername(dbUser);
    dataSource.setPassword(dbPassword);
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setPackagesToScan("com.tallerwebi.dominio");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  @Bean
  public HibernateTransactionManager transactionManager() {
    return new HibernateTransactionManager(sessionFactory(dataSource()).getObject());
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.format_sql", "true");
    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    return properties;
  }
}
