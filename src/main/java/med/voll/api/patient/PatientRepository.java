package med.voll.api.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

   Page<PatientEntity> findByActiveTrue(Pageable pagination);

}
