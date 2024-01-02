package com.codegym.spb_eyesclinic_project.service.staff;


import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.User;
import com.codegym.spb_eyesclinic_project.repository.CustomerRepository;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import com.codegym.spb_eyesclinic_project.repository.UserRepository;
import com.codegym.spb_eyesclinic_project.service.customer.request.CustomerSaveRequest;
import com.codegym.spb_eyesclinic_project.service.customer.response.CustomerResponse;
import com.codegym.spb_eyesclinic_project.service.response.SelectOptionResponse;
import com.codegym.spb_eyesclinic_project.service.staff.request.StaffSaveRequest;
import com.codegym.spb_eyesclinic_project.service.staff.response.StaffResponse;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

//    private final StaffRepository staffRepository;

//
//    public Staff findById(Long id) {
//        return staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
//    }
//
//    public Staff findByPhoneNumber(String phoneNumber) {
//        return staffRepository.findStaffByNumberPhone(phoneNumber).orElseThrow(() -> new RuntimeException("Not Found!"));
//    }
//
//    public StaffResponse getStaffResponseByID(Long id) {
//        return staffRepository.findById(id)
//                .map(staff -> AppUtils.mapper
//                        .map(staff, StaffResponse.class))
//                .orElseThrow(()-> new RuntimeException("Not found"));
//    }
//
//    public List<SelectOptionResponse> findAll() {
//        return staffRepository.findAll().stream()
//                .map(staff -> new SelectOptionResponse(staff.getId().toString(), staff.getUser().getFullName()))
//                .collect(Collectors.toList());
//    }

    public void createStaff(StaffSaveRequest request) {
        var staff = AppUtils.mapper.map(request, Staff.class);
        staffRepository.save(staff);
    }
}
