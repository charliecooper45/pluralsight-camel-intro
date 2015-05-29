package com.pluralsight.orderfulfillment.test;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TestIntegration {
	
	/**
	 * Creates and configures a DataSource
	 * @return the DataSource
	 */
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		dataSource.setUrl("jdbc:derby:memory:orders;create=true");
		return dataSource;
	}
	
	/**
	 * Creates a JdbcTemplate with a DataSource
	 * @return the JdbcTemplate
	 */
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource());
		return jdbc;
	}
	
	/**
	 * Creates a DerbyDatabaseBean for creating and destroying the Derby orders database
	 * @return the DerbyDatabaseBean
	 */
	@Bean(initMethod = "create", destroyMethod = "destroy")
	public DerbyDatabaseBean derbyDatabaseBean() {
		DerbyDatabaseBean derby = new DerbyDatabaseBean();
		derby.setJdbcTemplate(jdbcTemplate());
		return derby;
	}
}
