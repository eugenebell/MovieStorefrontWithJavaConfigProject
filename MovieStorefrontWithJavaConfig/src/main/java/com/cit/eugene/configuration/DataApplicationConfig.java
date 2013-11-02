package com.cit.eugene.configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@AnnotationDrivenConfig
@Import({ SecurityApplicationConfig.class, ApplicationConfig.class })
public class DataApplicationConfig {

	@Bean
	public DataSource dataSource() {
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
        //factory.setPackagesToScan("com.satesh.springjsfjpa.entity");
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
//    
//    @Bean
//    public EntityManager entityManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return entityManagerFactory.get;
//    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    
}
