package com.github.cloud.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@SpringBootApplication
public class TutuServerApplication {
    private static Logger logger = LoggerFactory.getLogger(TutuServerApplication.class);
    private ConcurrentHashMap<String, Set<Server>> serverMap = new ConcurrentHashMap<>();

    public static void  main(String[] args){
        SpringApplication.run(TutuServerApplication.class,args);
    }

    public static class Server{
        //ip地址
        private String ip;
        //port端口号
        private Integer port;

        public Server(){}

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
            if(this == o) return true;
            //o为null 或者 类型不同 返回false
            if(o == null || getClass() != o.getClass()) return false;
            //转换类型
            Server server = (Server) o;
            //判断ip属性
            if(!ip.equals(server.ip)) return false;
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
