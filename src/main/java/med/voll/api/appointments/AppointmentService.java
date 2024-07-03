package med.voll.api.appointments;

import med.voll.api.patients.PatientEntity;
import med.voll.api.patients.PatientRepository;
import med.voll.api.physicians.PhysicianEntity;
import med.voll.api.physicians.PhysicianRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
   private AppointmentsRepository repository;
   private PatientRepository patientRepository;
   private PhysicianRepository physicianRepository;

   public AppointmentService(AppointmentsRepository repository,
                             PatientRepository patientRepository,
                             PhysicianRepository physicianRepository) {
      this.repository = repository;
      this.patientRepository = patientRepository;
      this.physicianRepository = physicianRepository;
   }

   public void registerAppointment(AppointmentDto data) {
      var patient = patientRepository.findById(data.idPatient()).get();
      var physician = physicianRepository.findById(data.idPhysician()).get();

      var appointment = new AppointmentEntity(null, patient,
            physician, data.date());
      repository.save(appointment);
   }
}
