package com.github.cloud.loadbalancer.ribbon;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * 为每一个负载均衡客户端创建一个应用上下文
 */
public class SpringClientFactory extends NamedContextFactory<RibbonClientSpecification> {
    private static final String NAMESPACE = "ribbon";
    public SpringClientFactory(){
        super(Ri);
    }
}
