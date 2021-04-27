package com.jaya.function;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Data
@ToString
//@Scope(value = WebApplicationContext.SCOPE_REQUEST,proxyMode = ScopedProxyMode.INTERFACES)
public class ScopFunction implements IScopFunction {
    @Value("${test.abc}")
    private String abc;
    String scopeValue;

    @Override
    public String apply(String o) {
        if (scopeValue == null) {
            scopeValue = o;
        }
        return scopeValue;
    }
}
