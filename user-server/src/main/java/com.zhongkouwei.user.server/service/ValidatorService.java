package com.zhongkouwei.user.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidatorService {

    @Autowired
    private Validator validator;

    public <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<T> violation : constraintViolations) {
                msg.append(violation.getMessage()).append(";");
            }
            Assert.isTrue(Boolean.FALSE,msg != null ? msg.toString() : "");
        }
    }

}
