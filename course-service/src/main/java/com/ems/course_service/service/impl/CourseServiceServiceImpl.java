package com.ems.course_service.service.impl;

import com.ems.course_service.dto.CourseDto;
import com.ems.course_service.entity.Course;
import com.ems.course_service.entity.Enrollment;
import com.ems.course_service.exceptions.CourseAlreadyExistsException;
import com.ems.course_service.exceptions.CourseNotFoundException;
import com.ems.course_service.mapper.CourseMapper;
import com.ems.course_service.repository.CourseRepository;
import com.ems.course_service.repository.EnrollmentRepository;
import com.ems.course_service.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CourseServiceServiceImpl implements ICourseService {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void createCourse(CourseDto courseDto) {
        Optional<Course> courseOptional = repository.findByTitle(courseDto.getTitle());

        if(courseOptional.isPresent()){
            throw new CourseAlreadyExistsException("Course already exists with title - " + courseDto.getTitle());
        }

        Course course = CourseMapper.mapToCourse(courseDto, new Course());
        repository.save(course);
    }
    @Override
    public CourseDto fetchCourse(String title) {
        Course course = repository.findByTitle(title).orElseThrow(
                () -> new CourseNotFoundException("Course does not exists for title - " + title)
        );

        CourseDto courseDto = CourseMapper.mapToCourseDto(course, new CourseDto());
        return courseDto;
    }

    @Override
    public boolean updateCourse(CourseDto courseDto) {
        boolean isUpdated = false;
        if(courseDto.getTitle() == null){
            return isUpdated;
        }
        Course course = repository.findByTitle(courseDto.getTitle()).orElseThrow(
                () -> new CourseNotFoundException("Course does not exists for Title - " + courseDto.getTitle())
        );

        Course updatedCourse = CourseMapper.mapToCourse(courseDto, course);
        repository.save(updatedCourse);
        isUpdated = true;

        return isUpdated;
    }
    @Override
    public boolean deleteCourse(String title) {
        boolean isDeleted = false;
        if(title == null){
            return isDeleted;
        }
        Course course= repository.findByTitle(title).orElseThrow(
                () -> new CourseNotFoundException("Course does not exists for Title - " + title)
        );
        repository.deleteByTitle(title);
        enrollmentRepository.deleteByCourseId(course.getId());

        isDeleted = true;
        return isDeleted;
    }
    @Override
    public List<CourseDto> fetchAllCourse(){
        List<Course> courses = repository.findAll();
        List <CourseDto> courseDtos= new ArrayList<>();


        for (Course course : courses) {
            // Process each employee
            courseDtos.add(CourseMapper.mapToCourseDto(course, new CourseDto()));
        }
        return courseDtos;



    }

    private final RestTemplate restTemplate;


    public CourseServiceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }


    public Integer fetchEmployeeId(String mobileNumber) {
        String url = "http://first:8090/api/fetchId?mobileNumber=" + mobileNumber;

        return restTemplate.getForObject(url, Integer.class);
    }

    @Override
    public boolean createEnrollment(Integer courseId, String mobileNumber) {
        try {

            Integer empId = this.fetchEmployeeId(mobileNumber);

            if (empId == null) {
                return false; // Employee ID not found
            }
            // Check if the enrollment exists
            boolean enrollmentExists = enrollmentRepository.existsByCourseIdAndEmpId(courseId, empId);
            if(!enrollmentExists){
//                Enrollment enrollment = new Enrollment( 101, courseId, empId, true);
//                Enrollment enrollment = new Enrollment( 101, 2001, 10001, true);
                Enrollment enrollment = new Enrollment();
//                enrollment.setId();
                enrollment.setCourseId(courseId);
                enrollment.setStatus(true);
                enrollment.setEmpId(empId);
                enrollmentRepository.save(enrollment);
                return true;

            }
            else{
                return false;
            }

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEnrollment(Integer empId) {

        boolean isDeleted = false;
        if(empId == null){
            return isDeleted;
        }
        enrollmentRepository.findByEmpId(empId).orElseThrow(
                () -> new CourseNotFoundException("Course does not exists for empId- " + empId)
        );
        enrollmentRepository.deleteByEmpId(empId);
        isDeleted=true;
        return isDeleted;
    }
}
