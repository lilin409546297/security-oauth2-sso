package com.cetc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.cetc.domain"})
@EnableJpaRepositories(basePackages = {"com.cetc.repository"})
@EnableJpaAuditing
@EnableTransactionManagement
public class DataSourceConfiguration {

}
