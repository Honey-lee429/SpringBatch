package com.example.servicereaderjob.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource springDS() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	public DataSource appDS() {
		return DataSourceBuilder.create().build();
	}

	/*
	 * The configured datasource bean used to connect to the database.
	 * It is injected using Spring's @Qualifier("appDS") annotation to ensure the correct datasource is used for
	 * transaction manager to guarantir atomicitade .*/
	@Bean
	public PlatformTransactionManager transactionManagerApp(@Qualifier("appDS") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
