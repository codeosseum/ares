package com.codeosseum.ares.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfiguration {
    private static final String MESSAGE_SOURCE_PATH = "classpath:messages";
    private static final String ENCODING = "UTF-8";

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_PATH);
        messageSource.setDefaultEncoding(ENCODING);

        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        bean.setValidationMessageSource(messageSource());
        
        return bean;
    }
}
