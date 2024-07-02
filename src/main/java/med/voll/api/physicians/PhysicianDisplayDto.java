package med.voll.api.physicians;

public record PhysicianDisplayDto(
      Long id,
      String name,
      SpecialtyEnum specialty,
      String dni,
      String email
) {
   public PhysicianDisplayDto(PhysicianEntity ph) {
      this(ph.getId(), ph.getName(), ph.getSpecialty(), ph.getDni(), ph.getEmail());
   }
}
