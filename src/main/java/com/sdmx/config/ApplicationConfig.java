package com.sdmx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.sdmx.Application;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Configuration
@PropertySource(name="permission", value="classpath:permission.properties")
@PropertySource(name="db", value="classpath:persistence-pgsql.properties")
@PropertySource(name="app", value="classpath:application.properties")
@ComponentScan(basePackageClasses = Application.class)
//@EnableAspectJAutoProxy(proxyTargetClass = true)
class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}