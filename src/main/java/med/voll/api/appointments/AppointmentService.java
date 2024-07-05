package med.voll.api.appointments;

import jakarta.xml.bind.ValidationException;
import med.voll.api.infra.errors.IntegrityValidation;
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
      if (patientRepository.findById(data.idPatient()).isEmpty()) {
         throw new IntegrityValidation("Patient's ID not found");
      }
      if (data.idPhysician() != null && !physicianRepository.existsById(data.idPhysician())) {
         throw new IntegrityValidation("Physician's ID not found");
      }

      var patient = patientRepository.findById(data.idPatient()).get();

      var physician = selectPhysician(data);

      var appointment = new AppointmentEntity(null, patient,
            physician, data.date());

      repository.save(appointment);
   }

   private PhysicianEntity selectPhysician(AppointmentDto data) {
      if (data.idPhysician() != null) {
         return physicianRepository.getReferenceById(data.idPhysician());
      }
      if (data.specialty() == null) {
         throw new IntegrityValidation("You need to select a physician's specialty");
      }
      return physicianRepository.selectPhysicianSpecialtyAndDate(data.specialty(), data.date());
   }
}
