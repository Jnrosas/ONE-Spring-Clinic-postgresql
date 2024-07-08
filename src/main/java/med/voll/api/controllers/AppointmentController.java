package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
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

      var response = appointmentService.listAppointments(pagination);
      return ResponseEntity.ok(response); //shows only active
   }


   @DeleteMapping
   @Transactional
   @Operation(summary = "Delete appointment",
         description = "Delete or cancel an appointment by its ID")
   public ResponseEntity cancelAppointment(@RequestBody @Valid AppointmentCancelDto data) throws ValidationException {

      appointmentService.deactivateAppointment(data);
      return ResponseEntity.noContent().build();
   }

}
