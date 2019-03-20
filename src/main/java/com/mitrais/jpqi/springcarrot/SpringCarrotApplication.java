package com.mitrais.jpqi.springcarrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("model")
public class SpringCarrotApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringCarrotApplication.class, args);
	}

}
