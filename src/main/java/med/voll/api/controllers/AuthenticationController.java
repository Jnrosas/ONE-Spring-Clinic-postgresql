package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import med.voll.api.infra.security.JWTokenDto;
import med.voll.api.infra.security.TokenService;
import med.voll.api.user.UserAuthenticationDto;
import med.voll.api.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private TokenService tokenService;

   @PostMapping
   @Operation(summary = "Login", description = "Login using the patient's email and password")
   public ResponseEntity<JWTokenDto> userAuthentication(
         @RequestBody @Valid UserAuthenticationDto userAuthenticationDto) {

      Authentication authToken = new UsernamePasswordAuthenticationToken(
            userAuthenticationDto.email(), userAuthenticationDto.password());

      var authenticatedUser = authenticationManager.authenticate(authToken);

      var JWToken = tokenService
            .generateToken((UserEntity) authenticatedUser.getPrincipal());
      return ResponseEntity.ok(new JWTokenDto(JWToken));
   }
}
