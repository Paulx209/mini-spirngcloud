package com.github.cloud.openfeign.support;

import feign.Contract;
import feign.MethodMetadata;
import feign.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * feign支持Spring Mvc注解
 */
public class SpringMvcContract extends Contract.BaseContract {
    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        //TODO 解析接口注解
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation annotation, Method method) {
        //RequestMapping注解
        if(annotation instanceof PostMapping){
            //解析PostMapping注解
            PostMapping postMapping = (PostMapping) annotation;
            data.template().method(Request.HttpMethod.POST);
            String path = postMapping.value()[0];
            //如果@PostMapping注解中的value属性不以/开头，并且目前的path不以/开头
            if(!path.startsWith("/") && !data.template().path().endsWith("/")){
                path="/" + path;
            }
            data.template().uri(path,true);
        }else if(annotation instanceof GetMapping){
            //解析GetMapping注解
            GetMapping getMapping = (GetMapping) annotation;
            data.template().method(Request.HttpMethod.GET);
            String path = getMapping.value()[0];
            if(!path.startsWith("/") && !data.template().path().endsWith("/")){
                path = "/" + path;
            }
            data.template().uri(path,true);
        }
    }

    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        //TODO 解析参数
        return true;
    }
}
