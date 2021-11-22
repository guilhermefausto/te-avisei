package br.com.guilhermefausto.teavisei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TeAviseiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeAviseiApplication.class, args);
	}

}
