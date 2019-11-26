package com.example.postsapi;

import com.example.postsapi.config.PrefilterDocConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class PostsApiApplication {
//	@GetMapping("/")
//    public String home(){
//		return "GET /posts/";
//	}
	public static void main(String[] args) {
		SpringApplication.run(PostsApiApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		PrefilterDocConfig swaggerFilter = new PrefilterDocConfig();
		filterRegistrationBean.setFilter(swaggerFilter);
		return filterRegistrationBean;
	}

}
