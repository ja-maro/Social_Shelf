package com.quest.etna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class QuestWebJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestWebJavaApplication.class, args);
	}

}
