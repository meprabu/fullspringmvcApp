package org.ms.DmhThymeLeaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DmhThymeLeafApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DmhThymeLeafApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DmhThymeLeafApplication.class);
    }
	
}
