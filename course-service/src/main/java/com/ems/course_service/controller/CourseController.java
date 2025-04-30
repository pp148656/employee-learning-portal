package com.ems.course_service.controller;

import com.ems.course_service.dto.CourseDto;
import com.ems.course_service.dto.ResponseDto;
import com.ems.course_service.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private ICourseService iCourseService;

    @Operation(
            description = "Create new Course operations",
            summary = "Post API to create new course in the system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Success in creating course"

    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCourse(@RequestBody @Valid CourseDto courseDto){
        iCourseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto("Created successfully", "201")
        );
    }

    @GetMapping("/fetch")
    public ResponseEntity<CourseDto> fetchCourse(@RequestParam
                                                      @Valid String title){
        CourseDto courseDto = iCourseService.fetchCourse(title);
        return ResponseEntity.status(HttpStatus.OK).body(courseDto);
    }

    @GetMapping("/fetch-all")
    public List<CourseDto> fetchAllCourse(){

        //      Write code to fetch all employee
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCourse(@RequestBody @Valid CourseDto courseDto){
        boolean isUpdated =  iCourseService.updateCourse(courseDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto("Updated Successfully", "203"));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto("Not updated", "501"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCourse(@RequestParam
                                                   @Valid  String title){
        boolean isDeleted =  iCourseService.deleteCourse(title);
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

    @GetMapping("/build-info")
    public String buildInfo(){
        return buildVersion;
    }


    @PostMapping("/enrollment/create")
    public ResponseEntity<ResponseDto> createEnrollment(@RequestParam @Valid Integer course_id, @RequestParam @Valid String mobileNumber){
        boolean isCreated= iCourseService.createEnrollment(course_id,mobileNumber);
        if(isCreated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto("Enrolled Successfully", "202"));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto("Internal Server Error","502")
            );
        }

    }

    @DeleteMapping("/enrollment/delete")
    public ResponseEntity<ResponseDto> deleteEnrollment(@RequestParam @Valid Integer empId){
        boolean isDeleted= iCourseService.deleteEnrollment(empId);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto("Deleted Successfully", "202"));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto("Internal Server Error","502")
            );
        }

    }



}
