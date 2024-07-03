package med.voll.api;

import med.voll.api.appointments.AppointmentService;
import med.voll.api.appointments.AppointmentsRepository;
import med.voll.api.controllers.AppointmentController;
import med.voll.api.controllers.PatientController;
import med.voll.api.controllers.PhysicianController;
import med.voll.api.patients.PatientRepository;
import med.voll.api.patients.PatientService;
import med.voll.api.physicians.PhysicianRepository;
import med.voll.api.users.UserRepository;
import med.voll.api.users.UserService;
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
	public PatientController patientController(PatientRepository patientRepository,
															 PatientService patientService,
															 UserRepository userRepository,
															 UserService userService) {
		return new PatientController(patientRepository, patientService, userRepository,
				userService);
	}

	@Bean
	public AppointmentService appointmentService(AppointmentsRepository repository,
																PatientRepository patientRepository,
																PhysicianRepository physicianRepository) {
		return new AppointmentService(repository, patientRepository, physicianRepository);
	}
}
