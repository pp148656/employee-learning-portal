package com.ems.course_service.mapper;

import com.ems.course_service.dto.CourseDto;
import com.ems.course_service.entity.Course;

public class CourseMapper {
    public static CourseDto mapToCourseDto(Course course, CourseDto courseDto){
        courseDto.setTitle(course.getTitle());
        courseDto.setDuration(course.getDuration());
        courseDto.setAuthor(course.getAuthor());
        return courseDto;
    }
    public static Course mapToCourse(CourseDto courseDto, Course course){
        course.setTitle(courseDto.getTitle());
        course.setDuration(courseDto.getDuration());
        course.setAuthor(courseDto.getAuthor());
        return course;
    }
}
