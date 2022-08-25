package com.comrades.app;


import com.comrades.app.core.bases.MessageBundle;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Profile("!tests")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.comrades.app.persistence.repositories")
@ComponentScan(basePackages = "com.comrades.app")
public class SpringBeansConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources
                = new ClassPathResource[]{new ClassPathResource("server/app.properties"),
                    new ClassPathResource("server/infra.properties")};
        pspc.setLocations(resources);
        pspc.setLocalOverride(true);
        pspc.setIgnoreUnresolvablePlaceholders(false);
        return pspc;
    }

    @Bean
    public DataSource dataSource() throws NamingException {

        HikariConfig config = new HikariConfig();

//        config.setDriverClassName("org.postgresql.Driver");
//        config.setJdbcUrl("jdbc:postgresql://localhost:5432/comrades?schema=public");
//        config.setUsername("postgres");
//        config.setPassword("qwe123");

        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:mem:test");
        config.setUsername("sa");
        config.setPassword("");

        config.setAutoCommit(true);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(25);
        config.setKeepaliveTime(60000);
        config.setIdleTimeout(120000);
        config.setLeakDetectionThreshold(150000);
        config.setMaxLifetime(180000);
        config.setConnectionTimeout(3000);
        config.setValidationTimeout(2500);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);

        Resource initSchema = new ClassPathResource("jdbc/schema.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, ds);

        return ds;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setDataSource(dataSource());


        return txManager;
    }

    @Bean
    public MessageSource messageSource() {
        String basename = "classpath:i18n/messages";
        MessageBundle messageSource = new MessageBundle(basename);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validatorFactory() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

}
