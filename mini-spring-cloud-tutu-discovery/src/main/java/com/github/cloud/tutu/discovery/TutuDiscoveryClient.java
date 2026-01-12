package com.github.cloud.tutu.discovery;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
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

    public TutuDiscoveryClient(TutuDiscoveryProperties tutuDiscoveryProperties){
        this.tutuDiscoveryProperties = tutuDiscoveryProperties;
    }
    /**
     * 根据serviceId 找到对应的服务实例集合
     * @param serviceId
     * @return
     */
    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceName",serviceId);

        String response = HttpUtil.get(tutuDiscoveryProperties.getServerAddr() + "/list", paramMap);
        return JSONUtil.parseArray(response).stream().map(server -> {
            TutuServiceInstance tutuServiceInstance = new TutuServiceInstance();
            tutuServiceInstance.setServiceId(serviceId);

            JSONObject object = (JSONObject) server;
            String ip = object.getStr("ip");
            Integer port = object.getInt("port");
            tutuServiceInstance.setHost(ip);
            tutuServiceInstance.setPort(port);
            return tutuServiceInstance;
        }).collect(Collectors.toList());
    }

    /**
     * 获取所有的服务名称
     */
    @Override
    public List<String> getServices() {
        String response = HttpUtil.get(tutuDiscoveryProperties.getServerAddr() + "/listServiceNames",new HashMap<>());
        logger.info("query service instance list, response: {}", response);
        return JSON.parseArray(response, String.class);
    }

    @Override
    public String description() {
        return "Spring Cloud Tutu Discovery Client";
    }
}
