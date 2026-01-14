package com.github.cloud.examples;

import com.github.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("provider-application")
public interface EchoService {
    @PostMapping("echo")
    String echo();
}
