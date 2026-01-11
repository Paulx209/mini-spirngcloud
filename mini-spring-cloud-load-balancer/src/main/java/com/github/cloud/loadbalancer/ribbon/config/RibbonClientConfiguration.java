package com.github.cloud.loadbalancer.ribbon.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置ribbon默认组件
 */
@Configuration
public class RibbonClientConfiguration {

    @Value("${ribbon.client.name}")
    private String name;

    /**
     * 该类用来读取ribbon客户端的配置信息
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IClientConfig ribbonIclientConfig(){
        DefaultClientConfigImpl config = new DefaultClientConfigImpl();
        //读取对应配置项的内容，然后加载一些信息？
        config.loadProperties(name);
        return config;
    }

    /**
     * 该类用来确定服务是否存活
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IPing ribbonPing(IClientConfig config){
        return new DummyPing();
    }

    /**
     * 负载均衡的规则
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IRule ribbonRule (IClientConfig config){
        ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
        zoneAvoidanceRule.initWithNiwsConfig(config);
        return zoneAvoidanceRule;
    }

    /**
     * 该类负责获取实例列表
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServerList<Server> ribbonServerList(IClientConfig config){
        ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

    /**
     * 定时任务更新实例列表
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServerListUpdater ribbonServerListUpdater(IClientConfig config) {
        return new PollingServerListUpdater(config);
    }


    /**
     * 服务实例过滤器
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServerListFilter<Server> ribbonServerListFilter(IClientConfig config) {
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        filter.initWithNiwsConfig(config);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer ribbonLoadBalancer(IClientConfig config,ServerList<Server> serverList,ServerListFilter<Server> serverListFilter,IRule iRule,IPing iPing,ServerListUpdater serverListUpdater){
        return new ZoneAwareLoadBalancer(config,iRule,iPing,serverList,serverListFilter,serverListUpdater);
    }

}
