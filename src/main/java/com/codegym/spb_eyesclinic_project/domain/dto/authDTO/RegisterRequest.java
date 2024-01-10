package com.example.furnitureweb.model.dto.authDTO;



import com.example.furnitureweb.model.dto.locationDTO.LocationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class RegisterRequest implements Validator {

    @NotEmpty(message = "Vui lòng nhập Họ và Tên")
    private String fullName;

    @NotEmpty(message = "Vui lòng nhập SĐT")
    private String phoneNumber;

    @NotEmpty(message = "Vui lòng nhập tên tài khoản")
    private String username;

    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    private String password;

    @NotEmpty(message = "Vui lòng nhập email")
    private String email;

    @NotEmpty(message = "Vui lòng nhập địa chỉ")
    private String address;

    private LocationRequest location;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) o;
        String fullName = registerRequest.fullName;
        String phoneNumber = registerRequest.phoneNumber;
        String username = registerRequest.username;
        String password = registerRequest.password;
        String email = registerRequest.email;
        String address = registerRequest.address;
        if (fullName.length() < 5) {
            errors.rejectValue("fullName",
                    "fullName.length",
                    "Tên phải có ít nhất là 5 ký tự");
        }
        if (phoneNumber.isEmpty()) {
            errors.rejectValue("phoneNumber",
                    "phoneNumber.isEmpty",
                    "Số điện thoại không được để trống");
        }
        if (username.length() < 5) {
            errors.rejectValue("username",
                    "username.length",
                    "Tên đăng nhập phải có ít nhất là 5 ký tự");
        }
        if (password.length() < 5) {
            errors.rejectValue("password",
                    "password.length",
                    "Mật khẩu phải có ít nhất là 5 ký tự");
        }
        if (email.isEmpty() ) {
            errors.rejectValue("email",
                    "email.isEmpty",
                    "Email không được để trống");
        }
        if (address.isEmpty() ) {
            errors.rejectValue("email",
                    "address.isEmpty",
                    "Địa chỉ không được để trống");
        }
    }
}
