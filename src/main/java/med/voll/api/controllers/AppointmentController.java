package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.*;
import med.voll.api.patients.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
   private AppointmentService appointmentService;
   private AppointmentsRepository appointmentsRepository;

   public AppointmentController(AppointmentService appointmentService,
                                AppointmentsRepository appointmentsRepository) {
      this.appointmentService = appointmentService;
      this.appointmentsRepository = appointmentsRepository;
   }

   @PostMapping
   @Transactional
   @Operation(summary = "Post an appointment",
         description = "A patient can add an appointment with a physician")
   public ResponseEntity registerAppointment(@RequestBody @Valid AppointmentDto data) {

      var response = appointmentService.registerAppointment(data);

      return ResponseEntity.ok(response);
   }


   @GetMapping
   @Operation(summary = "Get appointments",
         description = "Get the list of appointments")
   public ResponseEntity<Page<AppointmentDisplayDto>> listAppointments(
         @PageableDefault(size = 3, sort = "date") Pageable pagination) {

      return ResponseEntity.ok(appointmentsRepository.findAll(pagination)
            .map(AppointmentDisplayDto::new));
   }

   @DeleteMapping("/{id}")
   @Transactional
   @Operation(summary = "Delete appointment",
         description = "Delete or cancel an appointment by its ID")
   public ResponseEntity cancelAppointment(@PathVariable Long id) throws ValidationException {
      if (!appointmentsRepository.existsById(id)) {
         throw new ValidationException("No such appointment found");
      }
      appointmentsRepository.deleteById(id);
      return ResponseEntity.noContent().build();
   }

}
