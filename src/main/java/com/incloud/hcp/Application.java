package com.incloud.hcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
//for compatibility wit a SAP java tomcat
//@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class, GsonAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {
	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application) { return
	 * application.sources(Application.class); }
	 */

	public static void main(String[] args) {
		System.setProperty("server.connection-timeout","300000");
		SpringApplication.run(Application.class, args);
	}

}