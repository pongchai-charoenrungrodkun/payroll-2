package payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new Employee("Luke", "Skywalker", "Jedi")));
			log.info("Preloading " + repository.save(new Employee("Anakin", "Skywalker", "Jedi")));
		};
	}
}

// curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"firstname": "Luke", "lastname": "Cage", "role": "Mutant"}'