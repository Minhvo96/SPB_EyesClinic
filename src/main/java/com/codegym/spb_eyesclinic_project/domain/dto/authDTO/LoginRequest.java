package com.example.furnitureweb.model.dto.authDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LoginRequest implements Validator{

    @NotEmpty(message = "Vui lòng nhập tên tài khoản")
    private String username;

    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    private String password;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginRequest loginRequest = (LoginRequest) o;
        String username = loginRequest.username;
        String password = loginRequest.password;

        if (username.isEmpty()) {
            errors.rejectValue("username",
                    "username.isEmpty",
                    "Tên đăng nhập không được để trống");
        }
        if (password.isEmpty()) {
            errors.rejectValue("password",
                    "password.isEmpty",
                    "Mật khẩu không được để trống");
        }
    }
}
