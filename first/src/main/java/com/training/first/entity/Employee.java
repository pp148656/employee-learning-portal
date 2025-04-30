package com.training.first.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message="should not be empty")
    private String name;
    private String email;

    @NotEmpty(message="should not be empty")
    @Pattern(regexp="(^$|[0-9]{10})",message="Mobile no should have 10digits")
    private String mobileNumber;

    public String designation;

    public String department;

}
//we can map columns using column if name we use is different
//