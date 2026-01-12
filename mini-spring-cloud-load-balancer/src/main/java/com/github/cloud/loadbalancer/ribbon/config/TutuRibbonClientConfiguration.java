package com.github.cloud.loadbalancer.ribbon.config;

import com.github.cloud.loadbalancer.ribbon.TutuServerList;
import com.github.cloud.tutu.TutuDiscoveryProperties;
import com.github.cloud.tutu.discovery.TutuDiscoveryClient;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义ribbon组件
 */
@Configuration
public class TutuRibbonClientConfiguration {

    @Bean
    @ConditionalOnMissingBean //这里确定要添加该注解吗？
    public ServerList<?> ribbonServerList(IClientConfig config, TutuDiscoveryProperties tutuDiscoveryProperties){
        TutuServerList serverList = new TutuServerList(tutuDiscoveryProperties);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }
}
