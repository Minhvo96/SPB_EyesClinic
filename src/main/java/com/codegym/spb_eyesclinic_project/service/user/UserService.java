package com.codegym.spb_eyesclinic_project.service.user;


import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Enum.ERole;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.service.user.response.UserListResponse;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    public User findByPhoneNumber(String phoneNumber){
        return userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(()-> new RuntimeException("Not found!"));
    }

    public List<UserListResponse> findAllUsers() {
        return userRepository.findAll().stream().map(item -> new UserListResponse(item.getId(),
                item.getFullName(),
                item.getPhoneNumber(),
                item.getRole().toString())).collect(Collectors.toList());
    }

    public void createUser(UserSaveRequest request) {
//        return userRepository.save(user);

        var user = AppUtils.mapper.map(request, User.class);
        userRepository.save(user);

        // lấy role trong request > kiểm tra nếu mà Staff thì call StaffRepo.save, nếu mà Cus thì call CusRepo.save
        // staff(userId, phoneNumber) cus(userId, phoneNumber)

//        if ("Staff".equals(request.getRole())) {
//            var staff = AppUtils.mapper.map(request, Staff.class);
//            staffRepository.save(staff);
//        } else if ("Cus".equals(request.getRole())) {
//            var customer = AppUtils.mapper.map(request, Customer.class);
//            customerRepository.save(customer);
//        }

        ERole role = ERole.valueOf(request.getRole());
        switch (role) {
            case ROLE_USER:
                var customer = AppUtils.mapper.map(request, Customer.class);
                customer.setUser(user);
                customerRepository.save(customer);
                break;
            case ROLE_ADMIN:
            case ROLE_RECEPTIONIST:
            case ROLE_DOCTOR:
                var staff = AppUtils.mapper.map(request, Staff.class);
                staff.setUser(user);
                staffRepository.save(staff);
                break;
            default:

                break;
        }
    }

    public String getUserRoleByPhoneNumber(String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Not found!"));

        // Lấy vai trò từ đối tượng User
        var role = String.valueOf(user.getRole());

        return role;
    }
}
