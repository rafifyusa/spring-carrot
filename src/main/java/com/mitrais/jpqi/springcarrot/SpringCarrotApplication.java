package com.mitrais.jpqi.springcarrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("model")
@EnableJpaRepositories("repository")
public class SpringCarrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCarrotApplication.class, args);
	}

}
