package com.training.first.service;

import com.training.first.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {
    void createEmployee(EmployeeDto employeeDto);

    EmployeeDto fetchEmployee(String mobileNumber);

    boolean updateEmployee(EmployeeDto employeeDto);

    boolean deleteEmployee(String mobileNumber);

    Integer fetchId(String mobileNumber);

    List<EmployeeDto> fetchAllEmployee();
}
