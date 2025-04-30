package com.ems.course_service.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    public String title;
    public String duration;
    public String author;
}
