package com.jokerstation.bookkeeping.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jokerstation.bookkeeping.interceptor.AdminInterceptor;
import com.jokerstation.bookkeeping.interceptor.AppInterceptor;
import com.jokerstation.bookkeeping.interceptor.ConsoleInterceptor;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(objectMapper);
		return converter;
	}
	
	/**
	 * 添加Interceptor
	 */
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(consoleInterceptor()).addPathPatterns("/console/**");
		 registry.addInterceptor(appInterceptor()).addPathPatterns("/app/**");
		 registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**");
	}
	
	@Bean
	public ConsoleInterceptor consoleInterceptor() {
		return new ConsoleInterceptor();
	}
	
	@Bean
	public AppInterceptor appInterceptor() {
		return new AppInterceptor();
	}
	
	@Bean
	public AdminInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	
	/**
	 * 添加filter
	 */
	
	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> encodingFilter() {
		FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new CharacterEncodingFilter());
		registration.addUrlPatterns("/*");
		registration.setName("encodingFilter");
		registration.addInitParameter("encoding", "UTF-8");
		registration.addInitParameter("forceEncoding", "true");
		return registration;
	}
	
}
