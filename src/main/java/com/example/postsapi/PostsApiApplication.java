package com.example.postsapi;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmlpull.v1.XmlPullParserException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2
@RestController
public class PostsApiApplication {

	@GetMapping("/")
    public String home(){
		return "GET /posts/";
	}
	public static void main(String[] args) {
		SpringApplication.run(PostsApiApplication.class, args);
	}

	@Bean
	public Docket api() throws IOException, XmlPullParserException, org.codehaus.plexus.util.xml.pull.XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model = (Model) reader.read(new FileReader("pom.xml"));
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.postsapi.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(new ApiInfo("Account Service Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, (String) null, null, null));
	}
}
