package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.Staff;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.medicineDTO.MedicineResponse;
import com.codegym.spb_eyesclinic_project.repository.StaffRepository;
import com.codegym.spb_eyesclinic_project.service.staff.StaffService;
import com.codegym.spb_eyesclinic_project.service.staff.request.StaffSaveRequest;
import com.codegym.spb_eyesclinic_project.service.staff.response.StaffResponse;
import com.codegym.spb_eyesclinic_project.service.user.UserServices;
import com.codegym.spb_eyesclinic_project.service.user.request.UserSaveRequest;
import com.codegym.spb_eyesclinic_project.service.user.response.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staffs")
@AllArgsConstructor
public class StaffRestController {

    private UserServices userService;
    private StaffRepository staffRepository;
    private StaffService staffService;

    @GetMapping("")
    public ResponseEntity<?> getAllStaffs() {
        List<StaffResponse> staffResponseList = staffRepository.findStaffByStatus(EStatus.WORKING).stream().map(staff ->
                new StaffResponse(staff.getId(),
                        staff.getUser().getFullName(),
                        staff.getUser().getPhoneNumber(),
                        staff.getUser().getAddress(),
                        staff.getExperience(),
                        staff.getUser().getRole().toString(),
                        staff.getDegree().toString(),
                        staff.getAvatar().getFileUrl()
                )).collect(Collectors.toList());
        return new ResponseEntity<>(staffResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById(@PathVariable Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return new ResponseEntity<>(staff.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editStaff(@PathVariable Long id, @RequestBody Staff staff) {
        staffRepository.save(staff);
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }
}
