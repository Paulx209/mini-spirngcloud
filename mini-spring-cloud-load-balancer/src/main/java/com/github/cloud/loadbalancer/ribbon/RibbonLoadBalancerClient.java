package com.github.cloud.loadbalancer.ribbon;

import cn.hutool.core.util.StrUtil;
import com.github.cloud.tutu.TutuServiceInstance;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.Request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * ribbon负载均衡客户端 提供choose方法负责选择服务实例
 */
public class RibbonLoadBalancerClient implements LoadBalancerClient {

    private SpringClientFactory clientFactory;

    public RibbonLoadBalancerClient(SpringClientFactory springClientFactory) {
        this.clientFactory = springClientFactory;
    }

    /**
     * 选择服务实例
     *
     * @param serviceId
     * @return
     */
    @Override
    public ServiceInstance choose(String serviceId) {
        return choose(serviceId, null);
    }

    /**
     * 选择服务实例
     *
     * @param serviceId
     * @param request
     * @param <T>
     * @return
     */
    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        ILoadBalancer loadBalancer = clientFactory.getInstance(serviceId, ILoadBalancer.class);
        Server server = loadBalancer.chooseServer("default");
        if (server != null) {
            return new TutuServiceInstance(serviceId, server.getHost(), server.getPort());
        }
        return null;
    }

    /**
     * 重建请求URI，将服务名称替换为服务实例的IP:端口
     * @param instance
     * @param original
     * @return
     */
    @Override
    public URI reconstructURI(ServiceInstance instance, URI original) {
        try {
            //http://provider-application/echo  -> http://192.168.100.1:2131/echo
            StringBuilder sb = new StringBuilder();
            String scheme = original.getScheme(); // http
            String host = instance.getHost();    // ip
            int port = instance.getPort();      // port
            String rawPath = original.getRawPath(); //echo

            sb.append(scheme).append("://").append(host).append(":").append(port).append(rawPath);
            if (StrUtil.isNotEmpty(original.getRawQuery())) {
                sb.append("?").append(original.getRawQuery());
            }
            return new URI(sb.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行请求
     * @param serviceId
     * @param request
     * @return
     * @param <T>
     * @throws IOException
     */
    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        ServiceInstance serviceInstance = choose(serviceId);
        return execute(serviceId,serviceInstance,request);
    }

    /**
     * 执行http请求
     * @param serviceId
     * @param serviceInstance
     * @param request
     * @return
     * @param <T>
     * @throws IOException
     */
    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
        try {
            return request.apply(serviceInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
