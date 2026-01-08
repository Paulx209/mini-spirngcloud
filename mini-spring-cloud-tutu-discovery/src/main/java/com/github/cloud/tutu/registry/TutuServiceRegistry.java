package com.github.cloud.tutu.registry;

import cn.hutool.http.HttpUtil;
import com.github.cloud.tutu.TutuDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 具体的服务注册 实现类
 */
public class TutuServiceRegistry implements ServiceRegistry<Registration> {
    private Logger logger = LoggerFactory.getLogger(TutuServiceRegistry.class);

    private TutuDiscoveryProperties tutuDiscoveryProperties;

    public TutuServiceRegistry(TutuDiscoveryProperties tutuDiscoveryProperties){
        this.tutuDiscoveryProperties = tutuDiscoveryProperties;
    }
    /**
     * 服务注册
     * @param registration
     */
    @Override
    public void register(Registration registration) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("serviceName",tutuDiscoveryProperties.getService());
        paramMap.put("ip",tutuDiscoveryProperties.getIp());
        paramMap.put("port",tutuDiscoveryProperties.getPort());

        String result = HttpUtil.post(tutuDiscoveryProperties.getServerAddr() + "/register", paramMap);
        if(Boolean.parseBoolean(result)){
            logger.info("register service successfully , serviceName:{} ,ip:{} , port:{}",
                    tutuDiscoveryProperties.getService(), tutuDiscoveryProperties.getIp(),tutuDiscoveryProperties.getPort());
        }else{
            logger.error("register service failed , serviceName:{} ,ip:{} , port:{}",
                    tutuDiscoveryProperties.getService(), tutuDiscoveryProperties.getIp(),tutuDiscoveryProperties.getPort());
            throw new RuntimeException("register service failed,serviceName");
        }
    }

    /**
     * 服务销毁
     * @param registration
     */
    @Override
    public void deregister(Registration registration) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("serviceName",tutuDiscoveryProperties.getService());
        paramMap.put("ip",tutuDiscoveryProperties.getIp());
        paramMap.put("port",tutuDiscoveryProperties.getPort());

        String res = HttpUtil.post(tutuDiscoveryProperties.getServerAddr() + "/deregister", paramMap);
        if(Boolean.parseBoolean(res)){
            logger.info("deregister service successfully , serviceName:{} ,ip:{} , port:{}",
                    tutuDiscoveryProperties.getService(), tutuDiscoveryProperties.getIp(),tutuDiscoveryProperties.getPort());
        }else{
            logger.error("deregister service failed , serviceName:{} ,ip:{} , port:{}",
                    tutuDiscoveryProperties.getService(), tutuDiscoveryProperties.getIp(),tutuDiscoveryProperties.getPort());
            throw new RuntimeException("deregister service failed,serviceName");
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void setStatus(Registration registration, String status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getStatus(Registration registration) {
        return null;
    }
}
