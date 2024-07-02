package med.voll.api.patients;

public record PatientDisplayDto(
      Long id,
      String name,
      String dni,
      String phone_number
) {
   public PatientDisplayDto(PatientEntity patient) {
      this(patient.getId(), patient.getName(), patient.getDni(), patient.getPhone_number());
   }
}
