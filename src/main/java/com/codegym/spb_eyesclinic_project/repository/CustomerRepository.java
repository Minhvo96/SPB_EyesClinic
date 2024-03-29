package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.Booking;
import com.codegym.spb_eyesclinic_project.domain.Customer;
import com.codegym.spb_eyesclinic_project.domain.Enum.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT customer FROM Customer customer join User user on customer.user.id = user.id WHERE user.phoneNumber = :phone")
    Customer findCustomerByPhone(@Param("phone") String phone);

    @Query("SELECT customer FROM Customer customer join User user on customer.user.id = user.id WHERE user.phoneNumber like :keyword or  user.fullName like :keyword")
    List<Customer> findCustomerByPhoneOrFullName(@Param("keyword") String keyword);

}
