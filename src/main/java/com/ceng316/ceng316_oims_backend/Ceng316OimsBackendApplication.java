package com.ceng316.ceng316_oims_backend;

import com.ceng316.ceng316_oims_backend.User.Role;
import com.ceng316.ceng316_oims_backend.company.Company;
import com.ceng316.ceng316_oims_backend.company.RegistrationStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ceng316OimsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ceng316OimsBackendApplication.class, args);
		System.out.println("Hello Worlds");
		Company company = new Company("ewe","sd", Role.COMPANY,"sds",12321);
		company.setRegistrationStatus(RegistrationStatus.APPROVED);
	}

}
