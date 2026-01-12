package com.github.cloud.loadbalancer.ribbon;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.Request;

import java.io.IOException;
import java.net.URI;

/**
 * ribbon负载均衡客户端 提供choose方法负责选择服务实例
 */
public class RibbonLoadBalancerClient implements LoadBalancerClient {

    private SpringClientFactory

    @Override
    public ServiceInstance choose(String serviceId) {
        return null;
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        return null;
    }


    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        return null;
    }

    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
        return null;
    }

    @Override
    public URI reconstructURI(ServiceInstance instance, URI original) {
        return null;
    }
}
