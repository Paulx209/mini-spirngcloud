package com.github.cloud.loadbalancer.ribbon;

import com.github.cloud.loadbalancer.ribbon.config.RibbonClientConfiguration;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class SpringClientFactory extends NamedContextFactory<RibbonClientSpecification> {
    private static final String NAMESPACE = "ribbon";

    public SpringClientFactory() {
        super(RibbonClientConfiguration.class, NAMESPACE, "ribbon.client.name");
    }

    /**
     * 获取对应的Ribbon API 中类型为type的 instance   name -> context   context -> type -> bean
     * @param name
     * @param type
     * @return
     * @param <C>
     */
    @Override
    public <C> C getInstance(String name, Class<C> type) {
        return super.getInstance(name, type);
    }

    /**
     * 获取服务集群对应的子容器  name : context
     * @param name
     * @return
     */
    @Override
    protected AnnotationConfigApplicationContext getContext(String name) {
        return super.getContext(name);
    }
}
