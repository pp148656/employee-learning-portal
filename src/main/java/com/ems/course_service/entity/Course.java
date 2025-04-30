package com.ems.course_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message="Title should not be empty")
    private String title;
    @NotEmpty(message="Duration should not be empty")
    private String duration;

    @NotEmpty(message="Author should not be empty")
    private String author;

}
