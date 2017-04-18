package com.edvantis.training.parking.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.edvantis.training.parking")
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @PersistenceContext
    private EntityManagerFactory emFactory;

    private Flyway flywayInstance;

    public ApplicationConfig() {

    }

    @Bean
    public EntityManagerFactory getInstance() {
        return emFactory = getEntityManagerFactoryInstance();
    }

    public Flyway getFlywayInstance(String dbName, String login, String password) {
        flywayInstance = new Flyway();
        flywayInstance.setDataSource(dbName, login, password);
        return flywayInstance;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("entityManager");
    }

    // web

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    public InternalResourceViewResolver jspViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

}
