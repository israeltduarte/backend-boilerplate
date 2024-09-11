package br.isertech.com.easyfinancesback.controller;

import br.isertech.com.easyfinancesback.config.security.JwtProvider;
import br.isertech.com.easyfinancesback.constants.Messages;
import br.isertech.com.easyfinancesback.dto.JwtDTO;
import br.isertech.com.easyfinancesback.dto.LoginDTO;
import br.isertech.com.easyfinancesback.dto.UserDTO;
import br.isertech.com.easyfinancesback.entity.ITUser;
import br.isertech.com.easyfinancesback.error.exception.EmailAlreadyExistsException;
import br.isertech.com.easyfinancesback.error.exception.UserAlreadyExistsException;
import br.isertech.com.easyfinancesback.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO dto) {

        if (userService.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException(Messages.USERNAME_ALREADY_EXISTS);
        }
        if (userService.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(Messages.EMAIL_ALREADY_EXISTS);
        }
        ITUser user = userService.addUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> authenticateUser(@Valid @RequestBody LoginDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwt(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(new JwtDTO(jwt));
    }

}
