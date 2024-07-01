package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.patient.PatientEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

   @Value("${api.security.secret}")
   private String apiSecret;

   public String generateToken(PatientEntity patient) {
      try {
         Algorithm algorithm = Algorithm.HMAC256(apiSecret);
         return JWT.create()
               .withIssuer("vollmed_postgres")
               .withSubject(patient.getEmail()) //this is the username
               .withClaim("id", patient.getId())
               .withExpiresAt(expiryDate()) //token expiry duration
               .sign(algorithm); //this is the token
      }
      catch (JWTCreationException e) {
         throw new RuntimeException();
      }
   }

   private Instant expiryDate() {
      return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
   }

   public String getSubject(String token) {
      if (token == null) {
         throw new RuntimeException("Token invalid");
      }
      DecodedJWT verifier = null;
      try {
         Algorithm algorithm = Algorithm.HMAC256(apiSecret);
         verifier = JWT.require(algorithm)
               .withIssuer("vollmed_postgres")
               .build()
               .verify(token);
         verifier.getSubject();
      }
      catch (JWTVerificationException e) {
         System.out.println(e.toString());
      }
      if (verifier.getSubject() == null) {
         throw new RuntimeException("Verifier invalid");
      }
      return verifier.getSubject();
   }
}
