package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.patient.*;
import med.voll.api.physician.PhysicianEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
   //en lugar de usar @Autowired q imposibilita los unitary tests, creamos un constructor
   //que recibe el repositorio por param desde ApiApplication
   private PatientRepository patientRepository;

   public PatientController(PatientRepository patientRepository) {
      this.patientRepository = patientRepository;
   }

   @PostMapping
   public void registerPatient(@RequestBody @Valid PatientRegisterDto patientRegisterDTO) {
      patientRepository.save(new PatientEntity(patientRegisterDTO));
   }

   @GetMapping
   public Page<PatientDisplayDto> listPatients(
         @PageableDefault(size = 2, sort = "id") Pageable pagination) {
      return patientRepository.findByActiveTrue(pagination).map(PatientDisplayDto::new);//shows only active
//      return patientRepository.findAll(pagination).map(PatientDisplayDto::new);//show all
   }

   @PutMapping
   @Transactional
   public void updatePatient(@RequestBody @Valid PatientUpdateDto update) {
      PatientEntity patient = patientRepository.getReferenceById(update.id());
      patient.updateData(update);
   }

   @DeleteMapping("/{id}")
   @Transactional
   public void deletePatient(@PathVariable Long id) {
      PatientEntity patient = patientRepository.getReferenceById(id);
      patient.deactivatePatient();
   }
}
