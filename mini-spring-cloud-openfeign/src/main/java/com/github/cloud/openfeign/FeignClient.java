package com.github.cloud.openfeign;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FeignClient {

    /**
     * 服务提供者应用名称
     * @return
     */
    String value();
}
