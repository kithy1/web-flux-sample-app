package com.kithy.webfluxsampleapp;

import com.kithy.webfluxsampleapp.filters.RequestResponseLoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebFluxSampleAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxSampleAppApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	/**
	 * We may sometimes want a filter to only apply to certain URL patterns.
	 * In this case, we have to remove the @Component annotation from the filter class (in this case RequestResponseLoggingFilter) definition and register the filter using a FilterRegistrationBean.
	 */
//	@Bean
//	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
//		FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
//
//		registrationBean.setFilter(new RequestResponseLoggingFilter());
//		registrationBean.addUrlPatterns("/user/*");
//
//		return registrationBean;
//	}

}
