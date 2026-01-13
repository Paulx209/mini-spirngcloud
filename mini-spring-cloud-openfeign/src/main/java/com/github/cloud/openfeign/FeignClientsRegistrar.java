package com.github.cloud.openfeign;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Set;

public class FeignClientsRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * 往bean容器中注册feign客户端
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //1.获取到EnableFeignClients注解修饰的所在的包名
        String packageName = ClassUtils.getPackageName(importingClassMetadata.getClassName());

        //2.扫描packageName目录下面所有添加FeignClient注解的接口
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(packageName, FeignClient.class);

        for(Class<?> clazz : classes){
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            //使用FeignClientFactoryBean生成Feign客户端
            beanDefinition.setBeanClass(clazz);
            String clientName = clazz.getAnnotation(FeignClient.class).value();
            beanDefinition.getPropertyValues().addPropertyValue("contextId",clientName);
            beanDefinition.getPropertyValues().addPropertyValue("type",clazz);

            //将Feign客户端注册到bean容器中
            String beanName = clazz.getName();
            registry.registerBeanDefinition(beanName,beanDefinition);
        }
    }
}
