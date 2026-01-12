package com.github.cloud.loadbalancer.ribbon;

import org.springframework.cloud.context.named.NamedContextFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * ribbon 客户端配置
 */
public class RibbonClientSpecification implements NamedContextFactory.Specification {

    private String name;

    private Class<?>[] configuration;

    public RibbonClientSpecification() {}

    public RibbonClientSpecification(String name, Class<?>[] configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Class<?>[] configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null || o.getClass() != getClass()){
            return false;
        }
        RibbonClientSpecification that = (RibbonClientSpecification) o;
        return Objects.equals(that.name,this.name) && Arrays.equals(that.configuration,this.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Arrays.hashCode(configuration));
    }


    @Override
    public String toString() {
        return new StringBuilder("RibbonClientSpecification{").append("name='")
                .append(name).append("', ").append("configuration=")
                .append(Arrays.toString(configuration)).append("}").toString();
    }
}
