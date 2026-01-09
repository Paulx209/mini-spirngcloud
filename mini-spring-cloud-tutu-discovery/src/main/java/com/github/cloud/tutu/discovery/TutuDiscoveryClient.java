package com.github.cloud.tutu.discovery;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cloud.tutu.TutuDiscoveryProperties;
import com.github.cloud.tutu.TutuServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TutuDiscoveryClient implements DiscoveryClient {
    private static final Logger logger = LoggerFactory.getLogger(TutuDiscoveryClient.class);

    private TutuDiscoveryProperties tutuDiscoveryProperties;

    public TutuDiscoveryClient(TutuDiscoveryProperties tutuDiscoveryProperties) {
        this.tutuDiscoveryProperties = tutuDiscoveryProperties;
    }

    /**
     * 查询serviceId对应所有服务实例
     * @param serviceId
     * @return
     */
    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceName", serviceId);

        String response = HttpUtil.get(tutuDiscoveryProperties.getServerAddr() + "/list", paramMap);
        logger.info("query service instance, serviceId: {}, response: {}", serviceId, response);

        return JSON.parseArray(response).stream().map(hostInfo -> {
            TutuServiceInstance tutuServiceInstance = new TutuServiceInstance();
            tutuServiceInstance.setServiceId(serviceId);
            String ip = ((JSONObject) hostInfo).getString("ip");
            Integer port = ((JSONObject) hostInfo).getInteger("port");
            tutuServiceInstance.setHost(ip);
            tutuServiceInstance.setPort(port);
            return tutuServiceInstance;
        }).collect(Collectors.toList());
    }

    /**
     * 查询所有的服务名称集合
     * @return
     */
    @Override
    public List<String> getServices() {
        String response = HttpUtil.get(tutuDiscoveryProperties.getServerAddr() + "/listServiceNames", new HashMap<>());
        logger.info("query service instance list,response :{}",response);
        return JSONObject.parseArray(response,String.class);
    }

    @Override
    public String description() {
        return "Spring Cloud Tutu Discovery Client";
    }
}
