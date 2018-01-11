package com.sdmx.config;

import com.sdmx.controller.menu.MenuContainer;
import com.sdmx.controller.menu.MenuContainerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/";
    private static final String VIEWS = "/WEB-INF/views/";

    private static final String RESOURCES_LOCATION = "/resources/";
    private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5 MB

    public static final int PAGE_RECORD_SIZE = 20;
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    @Autowired
    ApplicationContext context;

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);

        return requestMappingHandlerMapping;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        String lang = "en";

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE + lang);
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding(CHARACTER_ENCODING);

        return messageSource;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix(VIEWS);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        resolver.setCacheable(false);

        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new Java8TimeDialect());

        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setCharacterEncoding(CHARACTER_ENCODING);

        return thymeleafViewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(MAX_UPLOAD_SIZE * 2);
        cmr.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE); //bytes

        return cmr;
    }

    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public MenuContainer menuContainer(HttpServletRequest request) {
        return new MenuContainerImpl(context, request);
    }

    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public Pageable pageable(HttpServletRequest request) {
        Map<String, String[]> httpQuery = request.getParameterMap();

        /** Pagination */
        int page = Integer.parseInt(httpQuery.getOrDefault("page", new String[]{"1"})[0]);

        // default
        if (page < 1) {
            page = 1;
        }

        page -= 1;

        /** Sorting */
        int i = 0;
        Sort.Order[] orders = {};
        String[] sorts = httpQuery.get("sort");

        if (sorts != null) {
            int sortLen = sorts.length;

            for (String column : httpQuery.get("col")) {
                Sort.Direction direction = Sort.Direction.ASC;

                if (sortLen <= i && sorts[i].equals("desc")) {
                    direction = Sort.Direction.DESC;
                }

                orders[i] = new Sort.Order(direction, column);
                i++;
            }

            if (orders.length > 0) {
                return new PageRequest(page, PAGE_RECORD_SIZE, new Sort(orders));
            }
        }

        return new PageRequest(page, PAGE_RECORD_SIZE);
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource((MessageSource) context.getBean("messageSource"));
//        validator.setValidationMessageSource(messageSource());

        return validator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
     */
    @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/img/favicon.ico";
        }
    }
}
