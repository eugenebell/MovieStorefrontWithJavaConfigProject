package com.cit.eugene.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({ApplicationConfig.class, SecurityApplicationConfig.class})
public class DataApplicationConfig {

	@Bean
	public DataSource dataSource() {
		//TODO put properties file here!
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/movies");
		ds.setUsername("root");
		ds.setPassword("password");
		return ds;
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setDataSource(dataSource());
        factory.setPersistenceUnitName("video-unit");
        //factory.afterPropertiesSet();
        //factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
       return factory;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
    	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.FALSE);
        vendorAdapter.setShowSql(Boolean.TRUE);
        vendorAdapter.setDatabase(Database.MYSQL);
        return vendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    //TODO use flyway instead
    @Value("classpath:mysql-schema.sql")
    private Resource schemaScript;

    @Value("classpath:sample-setup-data.sql")
    private Resource dataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }
    
}
