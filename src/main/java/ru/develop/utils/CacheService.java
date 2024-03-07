package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.entity.Fractionable;
import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

import java.lang.reflect.*;


@Slf4j
public class CacheService<T> implements InvocationHandler {

    private T o;
    private Double cacheValue;
    public CacheService(T o){
        this.o = o;
    };

    public static <T> T cache(T in){
        ClassLoader classLoader = in.getClass().getClassLoader();
        Class[] interfaces = in.getClass().getInterfaces();
        T out = (T) Proxy.newProxyInstance(classLoader,interfaces,new CacheService<>(in));
        return out;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Cache.class) && cacheValue ==null){
            return cacheValue = (Double) method.invoke((Fractionable)o,args);
        }else if (method.isAnnotationPresent(Cache.class) && cacheValue !=null){
            return cacheValue;
        }


        if(method.isAnnotationPresent(Mutator.class)){
            cacheValue = null;
        }

        return method.invoke((Fractionable)o,args);
    }

}
