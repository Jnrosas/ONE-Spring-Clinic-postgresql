package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.appointments.AppointmentDisplayDto;
import med.voll.api.appointments.AppointmentDto;
import med.voll.api.appointments.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
   private AppointmentService appointmentService;

   public AppointmentController(AppointmentService appointmentService) {
      this.appointmentService = appointmentService;
   }

   @PostMapping
   @Transactional
   @Operation(summary = "Post an appointment",
         description = "A patient can add an appointment with a physician")
   public ResponseEntity registerAppointment(@RequestBody @Valid AppointmentDto data) {

      appointmentService.registerAppointment(data);

      return ResponseEntity.ok(new AppointmentDisplayDto(
            null, data.idPatient(), data.idPhysician(), data.date(), data.specialty()));
   }
}
