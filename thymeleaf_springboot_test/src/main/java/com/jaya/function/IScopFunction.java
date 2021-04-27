package com.jaya.function;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.function.Function;

public interface IScopFunction extends Function<String, String> {

    @Override
    public String apply(String o);
}
