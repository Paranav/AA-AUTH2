package com.sikshagrih.aaAuth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;


@EnableScheduling
@Slf4j
@SpringBootApplication
public class AAAUTH2Application {

	public static void main(String[] args) {
		SpringApplication.run(AAAUTH2Application.class, args);
	}

}
