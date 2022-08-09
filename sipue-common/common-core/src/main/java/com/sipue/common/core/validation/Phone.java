package com.sipue.common.core.validation;


import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;

import static com.sipue.common.core.constants.RegexConstants.PHONE_REGEX;

/**
 * @Description 手机号码规则验证
 * @Author wangjunyu
 * @Date 2022年6月22日17:26:15
 * @Version 1.0
 */
@Documented
@Constraint(validatedBy = Phone.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "手机号验证不正确";
    Class[] groups() default {};
    Class[] payload() default {};

    class Validator implements ConstraintValidator<Phone, String> {

        @Override
        public boolean isValid(String phoneField, ConstraintValidatorContext context) {

            if (StringUtils.isEmpty(phoneField)) {
                return true;
            }
            return phoneField.matches(PHONE_REGEX);
        }

    }
}
