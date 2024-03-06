package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.entity.Fractionable;
import ru.develop.my.annotations.Cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class CacheService<T> {

    private static Method cacheMethod;
    public CacheService(){};
    public static <T> Fractionable cache(T cls){
        Class<?> inClass = cls.getClass();
        Method[] methods = inClass.getMethods();
        Field[] fields = cls.getClass().getDeclaredFields();

        for(Method m: methods){
            for (Annotation declaredAnnotation : m.getDeclaredAnnotations()) {
                if(m.isAnnotationPresent(Cache.class)){
                    cacheMethod = m;
                }
            }
        }
        return new OverrideClass(cacheMethod,(Fractionable)cls);
    }



    private static class OverrideClass implements Fractionable{
        Method backupMethod;
        Fractionable backupCls;
        boolean firstCall = false;
        double valueMethod;

        public OverrideClass(Method backupMethod,Fractionable backupCls){
            this.backupMethod = backupMethod;
            this.backupCls = backupCls;
        }
        @Override
        public double doubleValue() {
            if(firstCall == false){
                try {
                    firstCall = true;
                    return valueMethod = (double)backupMethod.invoke(backupCls);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("i am OVERRIDE method");
            log.info(String.format("my value: %f",valueMethod));
            return valueMethod;
        }

        @Override
        public void setNum(int num) {

        }

        @Override
        public void setDenum(int denum) {
        }
    };
}
