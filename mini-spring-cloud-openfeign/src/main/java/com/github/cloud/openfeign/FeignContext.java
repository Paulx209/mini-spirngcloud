package com.github.cloud.openfeign;

import org.springframework.cloud.context.named.NamedContextFactory;

public class FeignContext extends NamedContextFactory<FeignClientSpecification> {

    public FeignContext() {
        super(FeignClientsConfiguration.class, "feign", "feign.client.name");
    }
}
