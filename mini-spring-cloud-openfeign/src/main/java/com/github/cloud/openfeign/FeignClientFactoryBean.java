package com.github.cloud.openfeign;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * 生成feign客户端的factoryBean
 */
public class FeignClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

    private String contextId;

    private Class<?> type;

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getObject() throws Exception {
//        return Feign.builder().contract(new SpringMvcContract()).target(type,"http://localhost:1234");
        FeignContext bean = applicationContext.getBean(FeignContext.class);
        Encoder encoder = bean.getInstance(contextId, Encoder.class);
        Decoder decoder = bean.getInstance(contextId, Decoder.class);
        Contract contract = bean.getInstance(contextId, Contract.class);
        Client client = bean.getInstance(contextId, Client.class);

        return Feign.builder().encoder(encoder).decoder(decoder).contract(contract).client(client);
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }


}
