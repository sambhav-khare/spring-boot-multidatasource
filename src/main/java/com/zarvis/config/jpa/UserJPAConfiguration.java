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
import org.springframework.context.annotation.Primary;
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
@PropertySource("classpath:datasource-cfg.properties")
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory", basePackages = { "com.zarvis.repository" })
public class UserJPAConfiguration {
	@Autowired
	private Environment env;

	@Primary
	@Bean(name = "userDataSource")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().username(env.getProperty("spring.user.datasource.username"))
				.password(env.getProperty("spring.user.datasource.password"))
				.url(env.getProperty("spring.user.datasource.jdbcUrl"))
				.driverClassName(env.getProperty("spring.user.datasource.driver-class-name")).build();
	}

	@Primary
	@Bean(name = "userEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("userDataSource") DataSource userDataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.user.jpa.hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.user.jpa.hibernate.dialect"));
		return builder.dataSource(userDataSource).properties(properties).packages(SetDataSourcePackage.USER_MODEL)
				.persistenceUnit(SetUnit.USER).build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
