package com.jaya.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
//@ToString
//@Data()
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON,proxyMode = ScopedProxyMode.NO)
public class AnnoTest1 {
    @Value("${test.abc}")
    private String abc;
    @Autowired
    private AnnoTest annoTest;
}
