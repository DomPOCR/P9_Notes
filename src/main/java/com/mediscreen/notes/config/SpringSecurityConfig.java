package com.mediscreen.notes.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ResourceBundle;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Pour le log4j2
    final Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        logger.info("security connection start");
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String myUser = bundle.getString("application.security.user.name");
        String myPassword = bundle.getString("application.security.user.password");
        myPassword = "{noop}" + myPassword;

        logger.info("Connect : " + myUser + "  -  " + myPassword);
        auth.inMemoryAuthentication()
                .withUser(myUser)
                .password(myPassword)
                .roles("USER");
    }

    @Bean
    public HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
