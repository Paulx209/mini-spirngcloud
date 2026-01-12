package com.github.cloud.loadbalancer.ribbon;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Configuration(proxyBeanMethods = false)
@Import(RibbonClientConfigurationRegistrar.class)
public @interface RibbonClients {
    Class<?>[] defaultConfiguration() default {};
}
