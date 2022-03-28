package ru.voronkovmm.dollar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DollarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DollarApplication.class, args);
	}
}
