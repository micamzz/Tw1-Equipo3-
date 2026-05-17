package com.tallerwebi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    /*
     * @Bean
     * public DataSource dataSource2() {
     * DriverManagerDataSource dataSource = new DriverManagerDataSource();
     * dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
     * dataSource.setUrl("jdbc:hsqldb:mem:db_");
     * dataSource.setUsername("sa");
     * dataSource.setPassword("");
     * return dataSource;
     * }
     *
     *
     * @Bean
     * public DataSource dataSource() {
     * DriverManagerDataSource dataSource = new DriverManagerDataSource();
     * dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
     * dataSource.setUrl("jdbc:mariadb://localhost:3306/tw1");
     * dataSource.setUsername("root");
     * dataSource.setPassword("1234");
     * return dataSource;
     * }
     *
     */

    /**
     * Toggle:
     * - To run with an in-memory DB set env `RUN_WITHOUT_DB=true` or start JVM with
     * `-DrunWithoutDb=true`.
     * - To revert to the original behavior, unset the env var or remove the JVM
     * system property (no code changes required).
     */
    @Bean
    public DataSource dataSource() {
        if (useInMemoryDatabase()) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("org.hsqldb.jdbcDriver");
            ds.setUrl("jdbc:hsqldb:mem:db_");
            ds.setUsername("sa");
            ds.setPassword("");
            return ds;
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        if (dbHost == null)
            dbHost = "localhost";
        if (dbPort == null)
            dbPort = "3306";
        if (dbName == null)
            dbName = "tw1";
        if (dbUser == null)
            dbUser = "root";
        if (dbPassword == null)
            dbPassword = "1234";

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
        if (useInMemoryDatabase()) {
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        } else {
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "create");
        }
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

    private boolean useInMemoryDatabase() {
        String prop = System.getProperty("runWithoutDb");
        if ("true".equalsIgnoreCase(prop))
            return true;
        String env = System.getenv("RUN_WITHOUT_DB");
        return "true".equalsIgnoreCase(env);
    }
}