package com.training.first.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String mobileNumber;
    private String designation;
    private String department;
}
//we didnt wanted to expose some of the fields like id
