package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.patient.*;
import med.voll.api.physician.PhysicianEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {
   //en lugar de usar @Autowired q imposibilita los unitary tests, creamos un constructor
   //que recibe el repositorio por param desde ApiApplication
   private PatientRepository patientRepository;
   private PatientService patientService;

   public PatientController(PatientRepository patientRepository, PatientService patientService) {
      this.patientRepository = patientRepository;
      this.patientService = patientService;
   }


   @PostMapping
   @Transactional
   @Operation(summary = "Post patient", description = "Add patients to the database")
   public ResponseEntity registerPatient(@RequestBody @Valid PatientRegisterDto patientRegisterDTO,
                                         UriComponentsBuilder uri) {
      PatientEntity patient = patientRepository.save(new PatientEntity(patientRegisterDTO));

      //this encrypts the patient's password
      patientService.encryptPassword(patient);

      PatientDisplayDto displayDto = new PatientDisplayDto(patient.getId(), patient.getName(),
            patient.getDni(), patient.getPhone_number());
      URI url = uri.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
      return ResponseEntity.created(url).body(displayDto);
   }

   @GetMapping
   @Operation(summary = "Get patients", description = "List all active patients by page")
   public ResponseEntity<Page<PatientDisplayDto>> listPatients(
         @PageableDefault(size = 2, sort = "id") Pageable pagination) {
      return ResponseEntity.ok(patientRepository.findByActiveTrue(pagination)
            .map(PatientDisplayDto::new));//shows only active

//      return ResponseEntity.ok(patientRepository.findAll(pagination)
//      .map(PatientDisplayDto::new));   //shows all
   }

   @PutMapping("/{id}")
   @Transactional
   @Operation(summary = "Update patient", description = "Updates patient chosen by its Id")
   public ResponseEntity<PatientDisplayDto> updatePatient(@RequestBody @Valid PatientUpdateDto update,
                             @PathVariable Long id) {
//      PatientEntity patient = patientRepository.getReferenceById(update.id());
//      patient.updateData(update);
      Optional<PatientEntity> patient = patientRepository.findById(id);
      if (patient.isPresent()) {
         PatientEntity patientEnt = patient.get();
         patientEnt.updateData(update);
         return ResponseEntity.ok(new PatientDisplayDto(patientEnt));
      }
      return null;
   }

   @DeleteMapping("/{id}")
   @Transactional
   @Operation(summary = "Delete patient",
         description = "Logically delete: become inactive a patient chose by its Id")
   public ResponseEntity deletePatient(@PathVariable Long id) {
      PatientEntity patient = patientRepository.getReferenceById(id);
      patient.deactivatePatient();
      return ResponseEntity.noContent().build();
   }
}
