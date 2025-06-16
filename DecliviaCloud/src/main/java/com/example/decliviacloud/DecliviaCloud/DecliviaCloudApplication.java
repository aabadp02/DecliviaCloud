package com.example.decliviacloud.DecliviaCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.decliviacloud.DecliviaCloud")
public class DecliviaCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecliviaCloudApplication.class, args);
	}

}
