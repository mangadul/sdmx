package com.sdmx.support.app;

import com.sdmx.Application;
import com.sdmx.error.exception.FormValidationException;
import com.sdmx.support.util.RuleValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class Validator implements Application {

    @Autowired
    private MessageSource messageSource;

    public Set<String> guessFieldRule(Field field) {
        Class<?> type = field.getType();
        Set<String> rules = new HashSet<>();

        // Not an attribute
        if (field.isAnnotationPresent(Transient.class)) {
            return rules;
        }

        // Read from Annotations
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getDeclaredAnnotation(Column.class);

            // Nullable
            if (!column.nullable()) {
                rules.add("required");
            }

            // Unique
            if (column.unique()) {
                rules.add("unique");
            }

            // Length
            if (type.isAssignableFrom(String.class)) {
                rules.add("max:" + column.length());
            }
        }

        // Read from Field Type
        // Numeric & Integer
        if (type.isAssignableFrom(Number.class)) {
            if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(Integer.class)) {
                rules.add("integer");
            }
            else {
                rules.add("numeric");
            }
        }

        // String
        if (type.isAssignableFrom(String.class)) {
            rules.add("string");
        }

        return rules;
    }

    public void validateAttributeOrFail(String fieldName, String rules, Object value) throws FormValidationException {
        for (String rule : StringUtils.split(rules, "|")) {
            try {
                String[] ruleParam = rule.split(":");
                String ruleName = ruleParam[0];

                if (ruleParam.length > 1) {
                    ruleParam = ruleParam[1].split(",");
                }
                else ruleParam = null;

                Method method = RuleValidatorUtils.class.getMethod(ruleName, Object.class);
                Boolean isValid = (Boolean) method.invoke(null, value, ruleParam);

                if (!isValid) {
                    throw new FormValidationException(
                        messageSource.getMessage("validation."+ruleName, new Object[] {fieldName}, Locale.US)
                    );
                }
            }
            catch (FormValidationException e) { throw e; }
            catch (Exception e) {}
        }
    }
}
