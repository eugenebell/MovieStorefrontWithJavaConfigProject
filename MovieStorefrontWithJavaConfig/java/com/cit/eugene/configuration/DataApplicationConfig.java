package com.cit.eugene.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

}
