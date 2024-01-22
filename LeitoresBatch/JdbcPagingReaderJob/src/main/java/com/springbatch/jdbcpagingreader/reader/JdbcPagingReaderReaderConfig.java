package com.springbatch.jdbcpagingreader.reader;

import javax.sql.DataSource;

import com.springbatch.jdbcpagingreader.dominio.Cliente;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class JdbcPagingReaderReaderConfig {
	@Bean
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(
		@Qualifier("appDataSource") DataSource dataSource,
		PagingQueryProvider queryProvider // config feita para consultas paginadas
	) {
		return new JdbcPagingItemReaderBuilder<Cliente>()
			.name("jdbcPagingReader")
			.dataSource(dataSource)
			.queryProvider(queryProvider)
			.pageSize(1)
			.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class)) // responsável por mapear o resultado da consulta num objeto de domínio
			.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider(
		@Qualifier("appDataSource") DataSource dataSource) {
			SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
			queryProvider.setDataSource(dataSource);
			queryProvider.setSelectClause("select *");
			queryProvider.setFromClause("from cliente");
			queryProvider.setSortKey("email"); // clausula de ordenação, por se tratar de consulta paginada precisa
			// ordernar os resultados para garantir em casos de reinicialização sempre traga os resultados
			// na mesma ordem. Necessario garantir ordenação única, atravéz da PK.
			return queryProvider;
	}
}
