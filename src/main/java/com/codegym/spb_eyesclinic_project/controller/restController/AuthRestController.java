package com.codegym.spb_eyesclinic_project.controller.restController;


import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.domain.dto.authDTO.LoginRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.authDTO.RegisterRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.jwt.JwtResponse;
import com.codegym.spb_eyesclinic_project.exception.DataInputException;
import com.codegym.spb_eyesclinic_project.exception.UnauthorizedException;
import com.codegym.spb_eyesclinic_project.service.auth.AuthService;
import com.codegym.spb_eyesclinic_project.service.jwt.JwtService;
import com.codegym.spb_eyesclinic_project.service.userService.IUserService;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      BindingResult result, Model model) throws IOException {

        authService.checkPhoneNumber(request, result);
        model.addAttribute("user", request);

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, BindingResult bindingResult, HttpServletResponse response) {
        authService.checkPhoneNumberAndPassword(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return AppUtils.mapErrorToResponse(bindingResult);
        }
        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnauthorizedException("Username or password invalid");
        }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = userService.findByPhoneNumber(request.getPhoneNumber());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    userOptional.get().getId(),
                    userOptional.get().getPhoneNumber(),
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
                        .body(jwt);

    }
}
