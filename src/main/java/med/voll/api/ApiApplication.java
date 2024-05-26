package med.voll.api;

import med.voll.api.controllers.PatientController;
import med.voll.api.controllers.PhysicianController;
import med.voll.api.patient.PatientRepository;
import med.voll.api.physician.PhysicianRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	//the following @Bean send the repositories to the controller's constructors as args
	//so that we don't use @Autowired to inject dependencies, and avoid testing issues.
	@Bean
	public PhysicianController physicianController(PhysicianRepository physicianRepository) {
		return new PhysicianController(physicianRepository);
	}

	@Bean
	public PatientController patientController(PatientRepository patientRepository) {
		return new PatientController(patientRepository);
	}
}
