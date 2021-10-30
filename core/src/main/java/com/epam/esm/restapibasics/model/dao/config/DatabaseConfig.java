package com.epam.esm.restapibasics.model.dao.config;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.restapibasics.model.dao.impl.TagDaoImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db-${spring.profiles.active}.properties")
@ComponentScan("com.epam.esm.restapibasics")
@EnableTransactionManagement
public class DatabaseConfig {

    private final ApplicationContext applicationContext;
    @Value("${driver}")
    private String driver;
    @Value("${url}")
    private String url;
    @Value("${login}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${pool-size}")
    private int poolSize;

    @Autowired
    public DatabaseConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setInitialSize(poolSize);
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
        return new TagDaoImpl(jdbcTemplate);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateDaoImpl(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
