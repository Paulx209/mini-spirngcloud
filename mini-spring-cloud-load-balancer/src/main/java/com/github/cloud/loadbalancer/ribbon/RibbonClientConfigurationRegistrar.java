package com.github.cloud.loadbalancer.ribbon;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 处理注解RibbonClients的配置类
 */
public class RibbonClientConfigurationRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(RibbonClients.class.getName(), true);

        if (attributes != null && attributes.containsKey("defaultConfiguration")) {
            //default.com.github.cloud.loadbalancer.ribbon.config.RibbonTutuAutoConfiguration
            String name = "default." + metadata.getClassName();
            registerClientConfiguration(registry, name, attributes.get("defaultConfiguration"));
        }
    }

    private void registerClientConfiguration(BeanDefinitionRegistry registry, Object name, Object configuration) {
        //1.创建一个BeanDefinitionBuilder 类型为RibbonClientSpecification的
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RibbonClientSpecification.class);
        //2.1 传入构造参数1 name
        builder.addConstructorArgValue(name);
        //2.2 传入构造参数2 TutuRibbonClientConfiguration.class
        builder.addConstructorArgValue(configuration);
        //3 注册BeanDefinition到map中
        registry.registerBeanDefinition(name + ".RibbonClientSpecification", builder.getBeanDefinition());
    }
}
