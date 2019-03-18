package com.mitrais.jpqi.springcarrot;

//import com.mitrais.jpqi.springcarrot.storage.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EntityScan("model")
//@EnableConfigurationProperties({
//		FileStorageProperties.class
//})
//@EnableJpaRepositories("repository")
public class SpringCarrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCarrotApplication.class, args);
	}

}
