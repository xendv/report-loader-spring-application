package com.xendv.ReportLoader;

import com.xendv.ReportLoader.service.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReportLoaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportLoaderApplication.class, args);
	}

	/*@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}*/
}
