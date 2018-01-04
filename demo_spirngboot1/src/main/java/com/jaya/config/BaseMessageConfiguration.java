package com.jaya.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
@Configuration
public class BaseMessageConfiguration extends ResourceBundleMessageSource{
	

	@Override
	public void addBasenames(String... basenames) {
		List<String> list = Arrays.asList(basenames);
		list.add("messages");
		super.addBasenames((String[])list.toArray());
	}
}
