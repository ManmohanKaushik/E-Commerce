package com.bikkadit.electronicstore.validate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidetor implements ConstraintValidator<ImageNameValid, String> {
    private Logger logger = LoggerFactory.getLogger(ImageNameValidetor.class);


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("Massage from isValid :{}", value);
        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}
