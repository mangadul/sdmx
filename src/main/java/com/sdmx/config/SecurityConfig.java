package com.sdmx.config;

import com.sdmx.data.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.sdmx.data.AccountService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private Environment env;

    @Autowired
    private AccountService accountService;

//    @PostConstruct
    public void initialize() {
        // initialize permission
        if (env.getProperty("permission.initialize") != "false") {
            PropertySource propertySource = ((AbstractEnvironment) env).getPropertySources().get("permission");
            int batchSize = Integer.parseInt(env.getProperty("hibernate.jdbc.batch_size"));

            if (propertySource instanceof MapPropertySource) {
                EntityManager em = entityManagerFactory.createEntityManager();

                try {
                    Map<String, Object> permissions = ((MapPropertySource) propertySource).getSource();
                    int i = 0;

                    em.getTransaction().begin();

                    for (Map.Entry<String, Object> item : permissions.entrySet()) {
                        String[] credentials = item.getValue().toString().split(",");

                        for (String credential : credentials) {
                            credential = credential.trim();
                            em.persist(new Permission(item.getKey()+"."+credential, credential));

                            i++;
                            if (i % batchSize == 0) {
                                em.flush();
                                em.clear();
                            }
                        }
                    }

                    em.getTransaction().commit();
                }
                catch (Exception e) {
                    e.printStackTrace();

                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }
                finally {
                    em.close();
                }
            }
        }
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", accountService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(true)
            .userDetailsService(accountService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .antMatchers("/", "/favicon.ico", "/resources/**", "/signup", "/about", "/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/signin")
                .permitAll()
                .failureUrl("/signin?error=1")
                .loginProcessingUrl("/authenticate")
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/signin?logout")
                .and()
            .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key("remember-me-key");
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}