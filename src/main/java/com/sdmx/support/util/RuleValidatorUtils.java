package com.sdmx.support.util;

import com.sdmx.error.exception.FormValidationException;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RuleValidatorUtils {

    public static boolean between(Object value, String[] param) {
        Double num = parseNumeric(value);

        return num > parseNumeric(param[0]) && num < parseNumeric(param[1]);
    }

    public static boolean date(Object value, String[] param) {
        String[] formats = new String[] {
            "yyyy/MM/dd",
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "dd-MM-yyyy",
        };

        for (String format : formats) {
            if (date_format(value, new String[] {format})) {
                return true;
            }
        }

        return false;
    }

    public static boolean date_format(Object value, String[] param) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(param[0]);
            sdf.setLenient(false);
            sdf.parse(value.toString());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean digits(Object value, String[] param) {
        return value.toString().length() != parseNumeric(param[0]);
    }

    public static boolean digits_between(Object value, String[] param) {
        Integer len = value.toString().length();

        return len > parseNumeric(param[0]).intValue() && len < parseNumeric(param[1]).intValue();
    }

    public static boolean email(Object value, String[] param) {
        return value.toString().matches("[\\w\\.]+@\\w+\\.[\\w\\.]+");
    }

    public static boolean in(Object value, String[] param) {
        return Arrays.asList(param).contains(value.toString());
    }

    public static boolean integer(Object value, String[] param) {
        return value.toString().matches("[-+]?\\d+");
    }

    public static boolean min(Object value, String[] param) {
        return parseNumeric(value) > parseNumeric(param[0]);
    }

    public static boolean max(Object value, String[] param) {
        return parseNumeric(value) < parseNumeric(param[0]);
    }

    public static boolean numeric(Object value, String[] param) {
        return value.toString().matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean not_in(Object value, String[] param) {
        return !in(value, param);
    }

    public static boolean required(Object value, String[] param) {
        return value != null && !value.toString().trim().isEmpty();
    }

    public static boolean string(Object value, String[] param) {
        return value instanceof String || value != null;
    }

    public static boolean unique(Object value, String[] param) {
        return true; // #dev
    }

    public static Double parseNumeric(Object value) {
        if (numeric(value, null)) {
            return Double.parseDouble(value.toString());
        }

        return 0.d;
    }
}
