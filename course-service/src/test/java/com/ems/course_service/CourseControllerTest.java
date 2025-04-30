package com.ems.course_service;

import com.ems.course_service.dto.CourseDto;
import com.ems.course_service.entity.Enrollment;
import com.ems.course_service.service.ICourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICourseService iCourseService;



    @Test
    @DisplayName("GET /api/course/fetch?title=Hi - Found")
    public void fetchCourse() throws Exception {

        CourseDto RECORD_1 = new CourseDto("Hi","23","APJ");

        Mockito.when(iCourseService.fetchCourse("Hi")).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/course/fetch?title=Hi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Hi")))
                .andExpect(jsonPath("$.duration", is("23")))
                .andExpect(jsonPath("$.author", is("APJ")));
    }
    @Test
    @DisplayName("POST /api/course/create - Create")
    public void createCourse() throws Exception {
        CourseDto RECORD_2 = new CourseDto("Hi","23","APJ");

        doNothing().when(iCourseService).createCourse(RECORD_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RECORD_2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Created successfully")))
                .andExpect(jsonPath("$.status", is("201")));
    }

    @Test
    @DisplayName("PUT /api/course/update - Update")
    public void updateCourse() throws Exception {
        CourseDto DTO_RECORD = new CourseDto("Hi","23","APJ");

        doReturn(false).when(iCourseService).updateCourse(DTO_RECORD);

//        Mockito.when(iEmployeeService.updateEmployee(DTO_RECORD)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DTO_RECORD)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("Not updated")))
                .andExpect(jsonPath("$.status", is("501")));
    }

    @Test
    @DisplayName("DELETE /api/course/delete?title=Hi - Delete")
    public void deleteCourse() throws Exception {
        Mockito.when(iCourseService.deleteCourse("Hi")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/course/delete?title=Hi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Deleted Successfully")))
                .andExpect(jsonPath("$.status", is("200")));
    }



}
