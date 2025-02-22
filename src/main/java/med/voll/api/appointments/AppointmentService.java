package med.voll.api.appointments;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.validations.AppointmentCancelValidator;
import med.voll.api.appointments.validations.AppointmentValidator;
import med.voll.api.infra.errors.IntegrityValidation;
import med.voll.api.patients.PatientRepository;
import med.voll.api.physicians.PhysicianEntity;
import med.voll.api.physicians.PhysicianRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
   private AppointmentsRepository repository;
   private PatientRepository patientRepository;
   private PhysicianRepository physicianRepository;
   private List<AppointmentValidator> validators;
   private List<AppointmentCancelValidator> cancelValidators;

   public AppointmentService(AppointmentsRepository repository,
                             PatientRepository patientRepository,
                             PhysicianRepository physicianRepository,
                             List<AppointmentValidator> validators,
                             List<AppointmentCancelValidator> cancelValidators) {
      this.repository = repository;
      this.patientRepository = patientRepository;
      this.physicianRepository = physicianRepository;
      this.validators = validators;
      this.cancelValidators = cancelValidators;
   }


   public AppointmentDisplayDto registerAppointment(AppointmentDto data) {
      if (patientRepository.findById(data.idPatient()).isEmpty()) {
         throw new IntegrityValidation("Patient's ID not found");
      }
      if (data.idPhysician() != null && !physicianRepository.existsById(data.idPhysician())) {
         throw new IntegrityValidation("Physician's ID not found");
      }
      // Validations
      validators.forEach(v -> {
         try {
            v.validate(data);
         } catch (ValidationException e) {
            throw new RuntimeException(e);
         }
      });
      var patient = patientRepository.findById(data.idPatient()).get();
      var physician = selectPhysician(data);
      if (physician == null) {
         throw new IntegrityValidation("No available physician for this specialty");
      }
      var appointment = new AppointmentEntity(null, patient,
            physician, data.date(), true, null);

      repository.save(appointment);

      return new AppointmentDisplayDto(appointment);
   }


   private PhysicianEntity selectPhysician(AppointmentDto data) {
      if (data.idPhysician() != null) {
         return physicianRepository.getReferenceById(data.idPhysician());
      }
      if (data.specialty() == null) {
         throw new IntegrityValidation("You need to select a physician's specialty");
      }
      return physicianRepository.selectPhysicianSpecialtyAndDate(
            data.specialty(), data.date());
   }


   public Page<AppointmentDisplayDto> listAppointments(Pageable pagination) {
      return repository.findByActiveTrue(pagination).map(AppointmentDisplayDto::new);
   }


   public void deactivateAppointment(AppointmentCancelDto data) throws ValidationException {
      if (!repository.existsById(data.id())) {
         throw new ValidationException("No such appointment found");
      }
      //Validation
      cancelValidators.forEach(v -> {
         try {
            v.validate(data);
         } catch (ValidationException e) {
            throw new RuntimeException(e);
         }
      });
      AppointmentEntity appointment = repository.getReferenceById(data.id());
      appointment.deactivateAppointment(data);
      repository.save(appointment);
   }
}
