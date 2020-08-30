package com.dextra.hp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import springfox.documentation.swagger2.mappers.ModelSpecificationMapper;
import springfox.documentation.swagger2.mappers.ModelSpecificationMapperImpl;

@Configuration
public class AppConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages"); //TODO path move to properties
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public ModelSpecificationMapper modelMapper() {
        return new ModelSpecificationMapperImpl();
    }
}
