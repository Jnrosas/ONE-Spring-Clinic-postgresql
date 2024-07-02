package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.physicians.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {
   //en lugar de usar @Autowired q imposibilita los unitary tests, creamos un constructor
   //que recibe el repositorio por param desde ApiApplication
   private PhysicianRepository physicianRepository;

   public PhysicianController(PhysicianRepository physicianRepository) {
      this.physicianRepository = physicianRepository;
   }

   @PostMapping
   @Transactional
   @Operation(summary = "Post physician", description = "Add physicians to the database")
   public ResponseEntity registerPhysician(
         @RequestBody @Valid PhysicianRegisterDto physicianRegisterDTO,
         UriComponentsBuilder uri) {
      PhysicianEntity phy = physicianRepository.save(new PhysicianEntity(physicianRegisterDTO));
      PhysicianDisplayDto displayDto = new PhysicianDisplayDto(phy.getId(), phy.getName(),
            phy.getSpecialty(), phy.getDni(), phy.getEmail());
      URI url = uri.path("/physicians/{id}").buildAndExpand(phy.getId()).toUri();
      return ResponseEntity.created(url).body(displayDto);
   }

   @GetMapping
   @Operation(summary = "Get physicians", description = "List all active physicians by page")
   public ResponseEntity<Page<PhysicianDisplayDto>> listPhysicians(
         @PageableDefault(size = 2, sort = "id") Pageable pagination) {
      return ResponseEntity.ok(physicianRepository.findByActiveTrue(pagination)
            .map(PhysicianDisplayDto::new));//shows only active

//      return physicianRepository.findAll(pagination).map(PhysicianDisplayDto::new);//shows all

      //Another way without creating a constructor in PhysicianDisplayDto.
//      return physicianRepository.findAll().stream()
//      .map(ph -> new PhysicianDisplayDto(ph.getName(), ph.getSpecialty(), ph.getDni(), ph.getEmail()))
//      .collect(Collectors.toList());
   }

   @PutMapping("/{id}")
   @Transactional
   @Operation(summary = "Update physician", description = "Updates physician chosen by its Id")
   public ResponseEntity<PhysicianDisplayDto> updatePhysician(@RequestBody @Valid PhysicianUpdateDto update,
                               @PathVariable Long id) {
      Optional<PhysicianEntity> phy = physicianRepository.findById(id);
      if (phy.isPresent()) {
         PhysicianEntity physician = phy.get();
         physician.updateData(update);
         return ResponseEntity.ok(new PhysicianDisplayDto(physician));
      }
      return null;
   }

   @DeleteMapping("/{id}")
   @Transactional
   @Operation(summary = "Delete physician",
         description = "Logically delete: become inactive a physician chose by its Id")
   public ResponseEntity deletePhysician(@PathVariable Long id) {
      PhysicianEntity physician = physicianRepository.getReferenceById(id);
//      physicianRepository.delete(physician); //we won't delete it from the ddbb, we'll do logical deletion
      physician.deactivatePhyisician();
      return ResponseEntity.noContent().build();
   }
}
