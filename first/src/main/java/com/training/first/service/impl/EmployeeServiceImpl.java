package com.training.first.service.impl;

import com.training.first.dto.EmployeeDto;
import com.training.first.entity.Employee;
import com.training.first.exceptions.EmployeeAlreadyExistsException;
import com.training.first.exceptions.EmployeeNotFoundException;
import com.training.first.mapper.EmployeeMapper;
import com.training.first.repository.EmployeeRepository;
import com.training.first.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = repository.findByMobileNumber(employeeDto.getMobileNumber());

        if(employeeOptional.isPresent()){
            throw new EmployeeAlreadyExistsException("Employee already exists with mobile number - " + employeeDto.getMobileNumber());
        }

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto, new Employee());
        repository.save(employee);
    }
    @Override
    public EmployeeDto fetchEmployee(String mobileNumber) {
        Employee employee = repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new EmployeeNotFoundException("Employee does not exists for mobile number - " + mobileNumber)
        );

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee, new EmployeeDto());
        return employeeDto;
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) {
        boolean isUpdated = false;
        if(employeeDto.getMobileNumber() == null){
            return isUpdated;
        }
        Employee employee = repository.findByMobileNumber(employeeDto.getMobileNumber()).orElseThrow(
                () -> new EmployeeNotFoundException("Employee does not exists for mobile number - " + employeeDto.getMobileNumber())
        );

        Employee updatedEmployee = EmployeeMapper.mapToEmployee(employeeDto, employee);
        repository.save(updatedEmployee);
        isUpdated = true;

        return isUpdated;
    }
    private final RestTemplate restTemplate;


    public EmployeeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }
    public void deleteEnrollment(Integer empId) {
        String url = "http://course-service:8080/api/course/enrollment/delete?empId=" + empId ;
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

    }
    @Override
    public boolean deleteEmployee(String mobileNumber) {

        boolean isDeleted = false;

        if(mobileNumber == null){
            return isDeleted;
        }
        Employee employee= repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new EmployeeNotFoundException("Employee does not exists for mobile number - " + mobileNumber)
        );
        deleteEnrollment(employee.getId());
        repository.deleteByMobileNumber(mobileNumber);
        isDeleted = true;
        return isDeleted;
    }
    @Override
    public List<EmployeeDto> fetchAllEmployee(){
        List<Employee> employees = repository.findAll();
        List <EmployeeDto> employeeDtos= new ArrayList<>();


            for (Employee employee : employees) {
                // Process each employee
                employeeDtos.add(EmployeeMapper.mapToEmployeeDto(employee, new EmployeeDto()));
            }
            return employeeDtos;



    }
     @Override
     public Integer fetchId(String mobileNumber){
         Employee employee = repository.findByMobileNumber(mobileNumber).orElseThrow(
                 () -> new EmployeeNotFoundException("Employee does not exists for mobile number - " + mobileNumber)
         );
         return employee.getId();
     }

}
