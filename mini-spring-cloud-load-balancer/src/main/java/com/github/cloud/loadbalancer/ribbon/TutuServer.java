package com.github.cloud.loadbalancer.ribbon;

import com.netflix.loadbalancer.Server;

public class TutuServer extends Server {

    public TutuServer(String host, int port) {
        super(host, port);
    }
}
