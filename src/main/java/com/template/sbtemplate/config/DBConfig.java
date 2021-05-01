package com.template.sbtemplate.config;

import com.template.sbtemplate.constant.Constants;
import com.template.sbtemplate.secrets.SecretManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DBConfig {

    private final SecretManager secretManager;

    @Autowired
    public DBConfig(SecretManager manager) {
        this.secretManager = manager;
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
            .username(secretManager.getDBCredentials(Constants.TEMPLATE_DB_NAME).getUser())
            .password(secretManager.getDBCredentials(Constants.TEMPLATE_DB_NAME).getPassword())
            .build();
    }


}
