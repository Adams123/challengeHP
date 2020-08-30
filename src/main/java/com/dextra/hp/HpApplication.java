package com.dextra.hp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@EnableWebMvc
@OpenAPIDefinition(
		info = @Info(
				title="MakeMagic API",
				version = "0.1"
		)
)
public class HpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HpApplication.class, args);
	}

}
