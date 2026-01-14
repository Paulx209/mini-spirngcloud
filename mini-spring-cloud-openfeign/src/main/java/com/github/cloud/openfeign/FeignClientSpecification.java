package com.github.cloud.openfeign;

import org.springframework.cloud.context.named.NamedContextFactory;

import java.util.Arrays;
import java.util.Objects;

public class FeignClientSpecification implements NamedContextFactory.Specification {

    private String name;

    private Class<?>[] configuration;

    public FeignClientSpecification(String name, Class<?>[] configuration){
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
    public boolean equals(Object object) {
        if(object  == this){
            return true;
        }
        if(object == null || object.getClass() != getClass()){
            return false;
        }
        FeignClientSpecification that = (FeignClientSpecification) object;
        return Objects.equals(that.name,this.name) && Arrays.equals(that.configuration,this.configuration);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(configuration);
        return result;
    }
}
