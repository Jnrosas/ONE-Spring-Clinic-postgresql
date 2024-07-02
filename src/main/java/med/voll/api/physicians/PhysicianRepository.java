package med.voll.api.physicians;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {

   Page<PhysicianEntity> findByActiveTrue(Pageable pagination);
}
