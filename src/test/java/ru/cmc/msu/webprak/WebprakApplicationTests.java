package ru.cmc.msu.webprak;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EntityScan( basePackages = {"ru.cmc.msu.webprak.entities"} )
class WebprakApplicationTests {

	@Test
	void contextLoads() {
	}

}
