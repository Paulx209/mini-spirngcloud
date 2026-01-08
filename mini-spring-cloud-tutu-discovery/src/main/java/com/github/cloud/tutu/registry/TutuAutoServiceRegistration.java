package com.github.cloud.tutu.registry;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

/**
 * 服务自动注册
 */
public class TutuAutoServiceRegistration extends AbstractAutoServiceRegistration<Registration> {
    private TutuRegistration tutuRegistration;

    protected TutuAutoServiceRegistration(ServiceRegistry<Registration> serviceRegistry,TutuRegistration tutuRegistration){
        super(serviceRegistry,null);
        this.tutuRegistration = tutuRegistration;
    }

    @Override
    protected Registration  getRegistration() {
        if(tutuRegistration.getPort() < 0){
            //设置服务端口号
            tutuRegistration.setPort(this.getPort().get());
        }
        return tutuRegistration;
    }
    @Override
    protected Object getConfiguration() {
        return tutuRegistration.getTutuDiscoveryProperties();
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    protected Registration getManagementRegistration() {
        return null;
    }

}
