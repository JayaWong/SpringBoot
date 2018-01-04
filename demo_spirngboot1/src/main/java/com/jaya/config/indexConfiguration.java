package com.jaya.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class indexConfiguration extends WebMvcConfigurerAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(indexConfiguration.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/thymeleaf/index");
		registry.addRedirectViewController("**/index", "/thymeleaf/index");
		registry.addRedirectViewController("**/index.html", "/thymeleaf/index");
		registry.addStatusController("/notFound", HttpStatus.NOT_FOUND);
		super.addViewControllers(registry);
	}
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		HandlerExceptionResolver resolver = new HandlerExceptionResolver() {
			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) {
				ex.printStackTrace();
				StackTraceElement[] stackTrace = ex.getStackTrace();
				log.debug("===========================================================================");
				for (StackTraceElement st : stackTrace) {
					log.debug(st.getClassName()+"."+st.getMethodName()+':'+st.getLineNumber()+"/n");
				}
				log.debug("===========================================================================");
				ModelAndView mv = new ModelAndView("/notFound");
				mv.addObject("ex", ex.toString()+"/"+stackTrace[0].getClassName()+"/"
				+stackTrace[0].getMethodName()+"/"+stackTrace[0].getLineNumber());
				return mv;
			}
		};
		exceptionResolvers.add(resolver);
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}
	
}
