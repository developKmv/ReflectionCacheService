package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.entity.Fractionable;
import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;


@Slf4j
public class CacheService<T> implements InvocationHandler {

    private T o;
    private Double cacheValue;
    private static HashMap<String,String> methodsInfo = new HashMap<>();
    public CacheService(T o){
        this.o = o;
    };

    public static <T> T cache(T in){
        Class<?> inClass = in.getClass();
        Method[] methods = inClass.getMethods();
        for(Method m: methods){
            if (m.isAnnotationPresent(Cache.class)){
                methodsInfo.put(m.getName(), "Cache");
            }else if(m.isAnnotationPresent(Mutator.class)){
                methodsInfo.put(m.getName(), "Mutator");
            }

        }

        ClassLoader classLoader = in.getClass().getClassLoader();
        Class[] interfaces = in.getClass().getInterfaces();
        T out = (T) Proxy.newProxyInstance(classLoader,interfaces,new CacheService<>(in));
        return out;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(methodsInfo.containsKey(method.getName()) && methodsInfo.get(method.getName()) == "Cache" &&cacheValue == null){
            return cacheValue = (Double) method.invoke((Fractionable)o,args);
        }else if (methodsInfo.containsKey(method.getName()) && methodsInfo.get(method.getName()) == "Cache"  && cacheValue !=null){
            return cacheValue;
        }


        if(methodsInfo.containsKey(method.getName()) && methodsInfo.get(method.getName()) == "Mutator"){
            cacheValue = null;
        }

        return method.invoke((Fractionable)o,args);
    }

}
