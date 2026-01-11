package com.github.cloud.loadbalancer.ribbon;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.cloud.tutu.TutuDiscoveryProperties;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询Tutu服务实例列表
 */
public class TutuServerList extends AbstractServerList<TutuServer> {
    private static  Logger logger = LoggerFactory.getLogger(TutuServerList.class);

    private TutuDiscoveryProperties discoveryProperties;

    private String serviceId;

    public TutuServerList(TutuDiscoveryProperties discoveryProperties){
        this.discoveryProperties = discoveryProperties;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        this.serviceId = iClientConfig.getClientName();
    }

    /**
     * 查询服务实例列表
     * @return
     */
    @Override
    public List<TutuServer> getInitialListOfServers() {
        return getServer();
    }

    /**
     * 查询服务实例列表
     * @return
     */
    @Override
    public List<TutuServer> getUpdatedListOfServers() {
        return getServer();
    }

    private List<TutuServer> getServer(){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("serviceName",serviceId);

        String response = HttpUtil.get(discoveryProperties.getServerAddr() + "/list", paramMap);
        return JSONUtil.parseArray(response).stream().map(server -> {
            JSONObject object = (JSONObject) server;
            String ip = object.getStr("ip");
            Integer port = object.getInt("port");
            return new TutuServer(ip,port);
        }).collect(Collectors.toList());
    }

    public String getServiceId() {
        return serviceId;
    }

}
