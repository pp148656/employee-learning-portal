package com.training.first.repository;

import com.training.first.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByMobileNumber(String mobileNumber);
    List<Employee>  findAll();
    @Transactional
    @Modifying
    void deleteByMobileNumber(String mobileNumber);
}
