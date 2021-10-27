package com.microservices.currencyexchangeservice.xray;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AwsXrayConfig {

    private String fixedSegmentName;

    @Bean
    public Filter TracingFilter() {
        return new AWSXRayServletFilter(fixedSegmentName);
    }
}
