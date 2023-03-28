package ru.cmc.msu.webprak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan( basePackages = {"ru.cmc.msu.webprak.entities"} )
public class WebprakApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebprakApplication.class, args);
	}

}
