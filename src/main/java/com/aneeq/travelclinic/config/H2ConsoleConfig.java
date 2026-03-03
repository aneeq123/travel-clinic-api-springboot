package com.aneeq.travelclinic.config;

import jakarta.servlet.Servlet;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2ServletRegistration() {
        JakartaWebServlet h2Servlet = new JakartaWebServlet();
        ServletRegistrationBean<Servlet> registrationBean =
                new ServletRegistrationBean<>(h2Servlet, "/h2-console/*");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
}