package com.jaya.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
//@ToString
//@Data
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON,proxyMode = ScopedProxyMode.NO)
public class AnnoTest {
    @Value("${test.abc}")
    private String abc;
    @Autowired
    private AnnoTest1 annoTest1;
    @FixxedValue("hello beanpost")
    private String testFix;
    public List<Integer> initList;

    @PostConstruct
    public void init() {
        initList = new ArrayList<>();
        initList.add(1);
        System.out.println(initList);
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
