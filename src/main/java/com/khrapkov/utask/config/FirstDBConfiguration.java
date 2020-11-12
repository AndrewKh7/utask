package com.khrapkov.utask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.khrapkov.utask.repository.first_db_repository",
        entityManagerFactoryRef = "firstDBEntityManager",
        transactionManagerRef = "firstDBTransactionManager"
)
public class FirstDBConfiguration {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.first-datasource")
    public DataSource firstDBDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean firstDBEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(firstDBDatasource());
        em.setPackagesToScan(
                new String[] { "com.khrapkov.utask.entity" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties properties = new Properties();
        properties.put("hibernate.dialect",
                env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.hibernate.ddl-auto"));
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager firstDBTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                firstDBEntityManager().getObject());
        return transactionManager;
    }
}
