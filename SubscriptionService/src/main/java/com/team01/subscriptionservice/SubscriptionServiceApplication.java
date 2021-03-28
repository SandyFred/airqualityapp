package com.team01.subscriptionservice;

import com.team01.subscriptionservice.jwtfilter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@SpringBootApplication
public class SubscriptionServiceApplication {

    @Bean
    public FilterRegistrationBean<?> jwtFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new JwtFilter());
        filter.addUrlPatterns("/api/v1/subscriber/*");
        return filter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionServiceApplication.class, args);
    }

}
