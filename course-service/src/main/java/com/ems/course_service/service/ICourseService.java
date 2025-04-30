package com.ems.course_service.service;

import com.ems.course_service.dto.CourseDto;

import java.util.List;

public interface ICourseService {
    void createCourse(CourseDto courseDto);
    List<CourseDto> fetchAllCourse();
    CourseDto fetchCourse(String title);
    boolean deleteCourse(String title);
    boolean updateCourse(CourseDto courseDto);

    boolean createEnrollment(Integer course_id,String mobileNumber);
    boolean deleteEnrollment(Integer empId);
}
