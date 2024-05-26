package med.voll.api.physician;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {

   Page<PhysicianEntity> findByActiveTrue(Pageable pagination);
}
