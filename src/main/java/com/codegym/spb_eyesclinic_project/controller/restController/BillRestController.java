package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.Bill;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillDateRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillRequest;
import com.codegym.spb_eyesclinic_project.domain.dto.billDTO.BillResponse;
import com.codegym.spb_eyesclinic_project.domain.dto.bookingDTO.BookingRequest;
import com.codegym.spb_eyesclinic_project.service.billService.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
@AllArgsConstructor


public class BillRestController {

    private final BillService billService;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BillRequest request){
        billService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getById(@PathVariable Long id) {
        BillResponse bill = billService.findShowDetailById(id);
        return new ResponseEntity<>(bill,HttpStatus.OK);
    }

    @PostMapping("/revenue")
    public ResponseEntity<?> getBillsByMonthYear(@RequestBody BillDateRequest billDateRequest) {
        return new ResponseEntity<>(billService.getBillsByMonthYear(billDateRequest),HttpStatus.OK);
    }

    @PostMapping("/revenue/year")
    public ResponseEntity<?> getBillsByYear(@RequestBody BillDateRequest billDateRequest) {
        return new ResponseEntity<>(billService.getBillsByYear(billDateRequest),HttpStatus.OK);
    }


}
