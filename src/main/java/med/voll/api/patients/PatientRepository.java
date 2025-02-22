package med.voll.api.patients;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

   Page<PatientEntity> findByActiveTrue(Pageable pagination);

   @Query("""
         select p.active from PatientEntity p
         where p.id = :idPatient
         """)
   Boolean findActiveById(Long idPatient);
}
