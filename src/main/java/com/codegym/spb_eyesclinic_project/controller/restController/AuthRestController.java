package com.example.furnitureweb.controller.restController;

import com.example.furnitureweb.exception.DataInputException;
import com.example.furnitureweb.exception.UnauthorizedException;
import com.example.furnitureweb.model.User;
import com.example.furnitureweb.model.dto.authDTO.LoginRequest;
import com.example.furnitureweb.model.dto.authDTO.RegisterRequest;
import com.example.furnitureweb.model.dto.jwt.JwtResponse;
import com.example.furnitureweb.model.dto.locationDTO.LocationRequest;
import com.example.furnitureweb.service.auth.AuthService;
import com.example.furnitureweb.service.jwt.JwtService;
import com.example.furnitureweb.service.locationService.LocationService;
import com.example.furnitureweb.service.userService.IUserService;
import com.example.furnitureweb.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {


    private AuthenticationManager authenticationManager;


    private JwtService jwtService;


    private IUserService userService;


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request,
                                      BindingResult result, Model model) throws IOException {

        authService.checkUsernameOrPhoneNumberOrEmail(request, result);
        model.addAttribute("user", request);
        new RegisterRequest().validate(request, result);

        if (result.hasErrors()) {
            return AppUtils.mapErrorToResponse(result);
        }

        try {
            authService.register(request);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        authService.checkUsernameAndPassword(request, bindingResult);
        new LoginRequest().validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return AppUtils.mapErrorToResponse(bindingResult);
        }
        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnauthorizedException("Username or password invalid");
        }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = userService.findByUsername(request.getUsername());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    userOptional.get().getId(),
                    userDetails.getUsername(),
                    userOptional.get().getFullName(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 1000)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);

    }
}
