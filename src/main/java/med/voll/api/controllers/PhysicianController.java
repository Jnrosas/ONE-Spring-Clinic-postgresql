package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.physician.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
   public void registerPhysician(
         @RequestBody @Valid PhysicianRegisterDto physicianRegisterDTO) {
      physicianRepository.save(new PhysicianEntity(physicianRegisterDTO));
   }

   @GetMapping
   public Page<PhysicianDisplayDto> listPhysicians(
         @PageableDefault(size = 2, sort = "id") Pageable pagination) {
      return physicianRepository.findByActiveTrue(pagination).map(PhysicianDisplayDto::new);//shows only active
//      return physicianRepository.findAll(pagination).map(PhysicianDisplayDto::new);//shows all

      //Another way without creating a constructor in PhysicianDisplayDto.
//      return physicianRepository.findAll().stream()
//      .map(ph -> new PhysicianDisplayDto(ph.getName(), ph.getSpecialty(), ph.getDni(), ph.getEmail()))
//      .collect(Collectors.toList());
   }

   @PutMapping
   @Transactional
   public void updatePhysician(@RequestBody @Valid PhysicianUpdateDto update) {
      PhysicianEntity physician = physicianRepository.getReferenceById(update.id());
      physician.updateData(update);
   }

   @DeleteMapping("/{id}")
   @Transactional
   public void deletePhysician(@PathVariable Long id) {
      PhysicianEntity physician = physicianRepository.getReferenceById(id);
//      physicianRepository.delete(physician); //we won't delete it from the ddbb, we'll do logical deletion
      physician.deactivatePhyisician();
   }
}
