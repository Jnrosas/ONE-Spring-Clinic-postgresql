package med.voll.api.physicians;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {

   Page<PhysicianEntity> findByActiveTrue(Pageable pagination);

   @Query("""
         select p from PhysicianEntity p
         where p.active = true and
         p.specialty = :specialty and
         p.id not in (
            select a.physician.id from AppointmentEntity a
            where a.date = :date
            )
         order by rand()
         limit 1
         """)
   PhysicianEntity selectPhysicianSpecialtyAndDate(SpecialtyEnum specialty, LocalDateTime date);
}
