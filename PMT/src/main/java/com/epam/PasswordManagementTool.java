package com.epam;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasswordManagementTool {

	public static void main(String[] args) {
		PasswordManagementTool pmt = new PasswordManagementTool();
		pmt.doProcess();
	}

	public void doProcess() {
		SpringApplication.run(PasswordManagementTool.class);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
