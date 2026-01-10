package com.github.cloud.tutu.discovery;

import com.github.cloud.tutu.TutuDiscoveryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TutuDiscoveryAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public TutuDiscoveryProperties tutuDiscoveryProperties(){
        return new TutuDiscoveryProperties();
    }

    @Bean
    public TutuDiscoveryClient discoveryClient(TutuDiscoveryProperties tutuDiscoveryProperties){
        return new TutuDiscoveryClient(tutuDiscoveryProperties);
    }
}
