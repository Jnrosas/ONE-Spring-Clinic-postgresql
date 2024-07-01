package med.voll.api.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
   @Autowired
   private PatientRepository patientRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;

   public void encryptPassword(PatientEntity patient) {
      patient.setPassword(passwordEncoder.encode(patient.getPassword()));
      patientRepository.save(patient);
   }
}
