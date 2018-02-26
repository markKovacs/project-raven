package com.raven.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LayoutDialectConfig {

    @Bean(name = "layoutDialect")
    public LayoutDialect getLayoutDialectBean() {
        return new LayoutDialect();
    }

}
