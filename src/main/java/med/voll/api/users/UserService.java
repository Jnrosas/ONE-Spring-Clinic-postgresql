package med.voll.api.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private UserRepository userRepository;
   private PasswordEncoder passwordEncoder;

   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   public void encryptPassword(UserEntity user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
   }
}
