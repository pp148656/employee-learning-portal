package com.training.first.Controller;

import com.training.first.dto.EmployeeDto;
import com.training.first.dto.ResponseDto;
import com.training.first.service.IEmployeeService;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.hibernate.internal.util.collections.ArrayHelper.forEach;


@Tag(
        name= "Employee controller for CRUD operations",
        description= "UKG Training",
        externalDocs = @ExternalDocumentation(
                url = "http://first.com",
                description = "The external doc description"
        )
)
@Validated
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Operation(
            description = "Create new Employee operations",
            summary = "Post API to create new employee in the system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Success in creating employee"

    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        iEmployeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto("Created successfully", "201")
        );
    }

    @GetMapping("/fetch")
    public ResponseEntity<EmployeeDto> fetchEmployee(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should have 10 digits") String mobileNumber){
        EmployeeDto employeeDto = iEmployeeService.fetchEmployee(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDto);
    }

    @GetMapping("/fetch-all")
    public List<EmployeeDto> fetchAllEmployee(){

        List<EmployeeDto> employeeDto= iEmployeeService.fetchAllEmployee();

        return ResponseEntity.status(HttpStatus.OK).body(employeeDto).getBody();

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        boolean isUpdated =  iEmployeeService.updateEmployee(employeeDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto("Updated Successfully", "203"));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto("Not updated", "501"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteEmployee(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should have 10 digits") String mobileNumber){
        boolean isDeleted =  iEmployeeService.deleteEmployee(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto("Deleted Successfully", "200"));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto("Not deleted", "501"));
        }
    }


    @GetMapping("/greet")
    public String greet(){
        return "Hello World!";
    }

    @GetMapping("/fetchId")
    public Integer fetchId(@RequestParam
                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should have 10 digits") String mobileNumber){
          Integer emp_id= iEmployeeService.fetchId(mobileNumber);
          return ResponseEntity.status(HttpStatus.OK).body(emp_id).getBody();
    }




    @GetMapping("/build-info")
    public String buildInfo(){
        return buildVersion;
    }

}