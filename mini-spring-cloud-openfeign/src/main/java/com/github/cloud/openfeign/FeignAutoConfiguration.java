package com.github.cloud.openfeign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAutoConfiguration {
    @Bean
    public FeignContext feignContext(){
        return new FeignContext();
    }
}
