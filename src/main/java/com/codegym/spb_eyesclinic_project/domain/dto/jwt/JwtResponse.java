package com.codegym.spb_eyesclinic_project.domain.dto.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String phoneNumber;
    private String fullName;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, Long id, String phoneNumber, String fullName, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + phoneNumber + '\'' +
                ", name='" + fullName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
