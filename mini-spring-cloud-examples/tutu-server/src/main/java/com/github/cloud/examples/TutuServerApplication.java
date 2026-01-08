package com.github.cloud.examples;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@SpringBootApplication
public class TutuServerApplication {
    private static Logger logger = LoggerFactory.getLogger(TutuServerApplication.class);
    private ConcurrentHashMap<String, Set<Server>> serverMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(TutuServerApplication.class, args);
    }

    /**
     * 服务注册接口
     *
     * @param serviceName
     * @param ip
     * @param port
     * @return
     */
    @PostMapping("register")
    public boolean register(@RequestParam("serviceName") String serviceName, @RequestParam("ip") String ip, @RequestParam("port") Integer port) {
        logger.info("register service , serviceName:{} , ip:{} , port :{}", serviceName, ip, port);
        serverMap.putIfAbsent(serviceName, Collections.synchronizedSet(new HashSet<>()));
        Server server = new Server(ip, port);
        serverMap.get(serviceName).add(server);
        return true;
    }

    /**
     * 服务注销接口
     *
     * @param serviceName
     * @param ip
     * @param port
     * @return
     */
    @PostMapping("deregister")
    public boolean deregister(@RequestParam("serviceName") String serviceName, @RequestParam("ip") String ip, @RequestParam("port") Integer port) {
        logger.info("deregister service , serviceName:{} , ip:{} , port :{}", serviceName, ip, port);
        Set<Server> servers = serverMap.get(serviceName);
        if (servers != null) {
            Server server = new Server(ip, port);
            servers.remove(server);
        }
        return true;
    }

    /**
     * 根据服务名称查询所有的Server
     *
     * @param serviceName
     * @return
     */
    @GetMapping("list")
    public Set<Server> list(@RequestParam("serviceName") String serviceName) {
        Set<Server> servers = serverMap.get(serviceName);
        logger.info("list service,serviceName:{},serverSet:{}", serviceName, JSON.toJSONString(servers));
        return servers;
    }

    /**
     * 查询所有的服务名称列表
     *
     * @return
     */
    @GetMapping("listServiceNames")
    public Enumeration<String> listServiceNames() {
        Enumeration<String> keys = serverMap.keys();
        logger.info("listServiceNames service,serviceNames : {}", JSON.toJSONString(keys));
        return keys;
    }


    public static class Server {
        //ip地址
        private String ip;
        //port端口号
        private Integer port;

        public Server() {
        }

        public Server(String ip, Integer port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public Integer getPort() {
            return port;
        }

        @Override
        public boolean equals(Object o) {
            //如果地址相同 直接返回true
            if (this == o) return true;
            //o为null 或者 类型不同 返回false
            if (o == null || getClass() != o.getClass()) return false;
            //转换类型
            Server server = (Server) o;
            //判断ip属性
            if (!ip.equals(server.ip)) return false;
            //判断port属性
            return port.equals(server.port);
        }

        @Override
        public int hashCode() {
            int result = ip.hashCode();
            result = 31 * result + port.hashCode();
            return result;
        }
    }
}
