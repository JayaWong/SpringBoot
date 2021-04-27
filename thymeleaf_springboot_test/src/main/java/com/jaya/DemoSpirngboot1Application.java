package com.jaya;

import com.jaya.demo.AnnoTest;
import com.jaya.demo.AnnoTest1;
import com.jaya.demo.TestBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

//@PropertySource("classpath:application.properties")
//@Configuration
//@SpringBootApplication(scanBasePackages = "com.jaya")
public class DemoSpirngboot1Application {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(DemoSpirngboot1Application.class, args);
		main1(args);
	}
	public static void main1(String[] args) throws IOException {
		AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext();
		aac.addBeanFactoryPostProcessor(f->{
			f.getBeanNamesIterator().forEachRemaining(s->{
				System.out.println(s);
			});
		});
		List<PropertySource<?>> ymlResources = new YamlPropertySourceLoader().load("application.yml", new ClassPathResource("application.yml"));
//		PropertySource<?> propertySource = new DefaultPropertySourceFactory().createPropertySource("application.yml", new EncodedResource(new ClassPathResource("application.yml")));
		for (PropertySource<?> ymlResource : ymlResources) {
			aac.getEnvironment().getPropertySources().addLast(ymlResource);
		}
		aac.register(TestBeanPostProcessor.class, PropertySourcesPlaceholderConfigurer.class, AnnoTest.class, AnnoTest1.class);
//		aac.scan("com.jaya");
		aac.refresh();

//		aac.addBeanFactoryPostProcessor(pspc);
//		pspc.postProcessBeanFactory(aac.getBeanFactory());
		AnnoTest scopFunction = (AnnoTest) aac.getBean("annoTest");
		System.out.println(scopFunction.initList);
		System.out.println(scopFunction.getAbc());

	}
//	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		RestTemplate restTemplate = new RestTemplate();
		return new PropertySourcesPlaceholderConfigurer();
	}
}
