package com.jaya.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class TestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                FixxedValue fixxedValue = field.getAnnotation(FixxedValue.class);
                if (fixxedValue != null) {
                    String value = fixxedValue.value();
                    field.setAccessible(true);
                    try {
                        field.set(bean, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                FixxedValue fixxedValue = field.getAnnotation(FixxedValue.class);
                if (fixxedValue != null) {
                    String value = fixxedValue.value();
                    field.setAccessible(true);
                    try {
                        System.out.println(field.get(bean));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }
}
