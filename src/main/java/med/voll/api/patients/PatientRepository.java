package med.voll.api.patients;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

   Page<PatientEntity> findByActiveTrue(Pageable pagination);

}
