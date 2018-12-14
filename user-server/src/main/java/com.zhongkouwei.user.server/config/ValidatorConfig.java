package com.zhongkouwei.user.server.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;

@Configurable
public class ValidatorConfig {
    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory()
                .getValidator();
    }
}
