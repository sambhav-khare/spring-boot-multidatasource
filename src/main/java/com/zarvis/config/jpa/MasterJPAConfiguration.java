package com.zarvis.config.jpa;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zarvis.constant.jpa.SetDataSourcePackage;
import com.zarvis.constant.jpa.SetUnit;

@Configuration
@EnableTransactionManagement
//@PropertySources({ @PropertySource("classpath:datasource-cfg.properties") })
@PropertySource("classpath:datasource-cfg.properties")
@EnableJpaRepositories(entityManagerFactoryRef = "masterEntityManagerFactory", basePackages = { "com.zarvis.master.repository" })
public class MasterJPAConfiguration {
	@Autowired
	private Environment env;

//	@Primary
	@Bean(name = "masterDataSource")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().username(env.getProperty("spring.master.datasource.username"))
				.password(env.getProperty("spring.master.datasource.password"))
				.url(env.getProperty("spring.master.datasource.jdbcUrl"))
				.driverClassName(env.getProperty("spring.master.datasource.driver-class-name")).build();
	}

//	@Primary
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("masterDataSource") DataSource masterDataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.master.jpa.hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.master.jpa.hibernate.dialect"));
		return builder.dataSource(masterDataSource).properties(properties).packages(SetDataSourcePackage.USER_MODEL)
				.persistenceUnit(SetUnit.MASTER).build();
	}

//	@Primary
	@Bean(name = "masterTransactionManager")
	public PlatformTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
