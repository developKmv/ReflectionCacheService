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

    public CacheService(){};
    public static <T> Fractionable cache(T cls){
        Class<?> inClass = cls.getClass();
        Method[] methods = inClass.getMethods();
        Field[] fields = cls.getClass().getDeclaredFields();

        for(Method m: methods){
            for (Annotation declaredAnnotation : m.getDeclaredAnnotations()) {
                if(m.isAnnotationPresent(Cache.class)){
                    return new OverrideClass((Fractionable)cls);
                }
            }
        }
        return (Fractionable)cls;
    }



    private static class OverrideClass implements Fractionable{
        Fractionable backupCls;
        boolean firstCall = false;
        double valueMethod;

        private int num;
        private int denum;

        public OverrideClass(Fractionable backupCls){
            this.backupCls = backupCls;
        }
        @Override
        public double doubleValue() {
            if(firstCall == false){
                firstCall = true;
                return valueMethod=backupCls.doubleValue();
            }
            System.out.println("i am OVERRIDE method");
            log.info(String.format("my value: %f",valueMethod));
            return valueMethod;
        }

        @Override
        public void setNum(int num) {
            backupCls.setNum(num);
            firstCall = false;
        }

        @Override
        public void setDenum(int denum) {
            backupCls.setDenum(denum);
            firstCall = false;
        }
    };
}
