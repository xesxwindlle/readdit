package com.readdit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.NamingStrategy;

// import com.readdit.util.CamelNamingMapper;

@Configuration
@EnableJdbcRepositories(basePackages = "com.readdit.repository")
public class JdbcConfig {

    // @Bean
    // public NamingStrategy namingStrategy() {
    // return new CamelNamingMapper();
    // }

}
