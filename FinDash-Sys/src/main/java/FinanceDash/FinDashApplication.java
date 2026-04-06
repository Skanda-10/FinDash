package FinanceDash;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.User;
import FinanceDash.UserService.POJO.UserStatus;
import FinanceDash.UserService.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinDashApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinDashApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			if (repository.findByEmail("admin@zorvyn.com").isEmpty()) {
				User initialAdmin = new User();
				initialAdmin.setName("System Admin");
				initialAdmin.setEmail("admin@zorvyn.com");
				initialAdmin.setRole(Role.ADMIN); // The "Master" key
				initialAdmin.setStatus(UserStatus.ACTIVE);
				repository.save(initialAdmin);
				System.out.println(">>> Initial Admin User Created: admin@zorvyn.com");
			}
		};
	}
}
