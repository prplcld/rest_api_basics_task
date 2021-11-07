package com.epam.esm.restapibasics.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm.restapibasics")
public class SpringConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    private static final String ERROR_BUNDLE = "error/error_message";

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
       registry.addConverter(new OrderTypeConverter());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(ERROR_BUNDLE);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }
}
