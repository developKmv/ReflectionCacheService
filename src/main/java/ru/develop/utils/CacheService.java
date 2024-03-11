package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.entity.Fractionable;
import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;


@Slf4j
public class CacheService<T> implements InvocationHandler {

    private T o;
    private Double cacheValue;
    public CacheService(T o){
        this.o = o;
        log.info("init");
    };

    public static <T> T cache(T in){
        Class<?> inClass = in.getClass();
        Method[] methods = inClass.getMethods();

        ClassLoader classLoader = in.getClass().getClassLoader();
        Class[] interfaces = in.getClass().getInterfaces();
        T out = (T) Proxy.newProxyInstance(classLoader,interfaces,new CacheService<>(in));
        return out;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method[] basicMethods = o.getClass().getMethods();
        for(Method m: basicMethods){
            if(m.getName()== method.getName() && m.isAnnotationPresent(Cache.class) && cacheValue == null){
                return cacheValue = (Double) m.invoke((Fractionable)o,args);
            }else if(m.getName()== method.getName() && m.isAnnotationPresent(Cache.class) && cacheValue != null){
                return cacheValue;
            }else if(m.getName()== method.getName() && m.isAnnotationPresent(Mutator.class)){
                cacheValue = null;
            }
        }
        return method.invoke((Fractionable)o,args);
    }

}
