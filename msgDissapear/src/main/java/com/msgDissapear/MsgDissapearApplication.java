package com.msgDissapear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsgDissapearApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgDissapearApplication.class, args);
	}

}
