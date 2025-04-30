package com.training.first;

import static org.hamcrest.CoreMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.training.first.dto.EmployeeDto;
import com.training.first.service.IEmployeeService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEmployeeService iEmployeeService;

    @Test
    @DisplayName("GET /api/fetch?mobileNumber=9876543210 - Found")
    public void fetchEmployee() throws Exception {

        EmployeeDto RECORD_1 = new EmployeeDto("Monica", "monica@test", "9876543210", "ENGINEER", "CODE DEV");

        Mockito.when(iEmployeeService.fetchEmployee("9876543210")).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/fetch?mobileNumber=9876543210")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Monica")))
                .andExpect(jsonPath("$.mobileNumber", is("9876543210")))
                .andExpect(jsonPath("$.email", is("monica@test")))
                .andExpect(jsonPath("$.designation", is("ENGINEER")));
    }

    @Test
    @DisplayName("POST /api/create - Create")
    public void createEmployee() throws Exception {
        EmployeeDto RECORD_2 = new EmployeeDto("Rachel", "rachel@test", "9876543211", "ANALYST", "CODE DEV");

        doNothing().when(iEmployeeService).createEmployee(RECORD_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RECORD_2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Created successfully")))
                .andExpect(jsonPath("$.status", is("201")));
    }

    @Test
    @DisplayName("PUT /api/update - Update")
    public void updateEmployee() throws Exception {
        EmployeeDto DTO_RECORD = new EmployeeDto("Ross", "ross@test", "9876543212", "ENGINEER", "CODE DEV");

        doReturn(false).when(iEmployeeService).updateEmployee(DTO_RECORD);

//        Mockito.when(iEmployeeService.updateEmployee(DTO_RECORD)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DTO_RECORD)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("Not updated")))
                .andExpect(jsonPath("$.status", is("501")));
    }

    @Test
    @DisplayName("DELETE /api/delete?mobileNumber=9876543210 - Delete")
    public void deleteEmployee() throws Exception {
        Mockito.when(iEmployeeService.deleteEmployee("9876543210")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/delete?mobileNumber=9876543210")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Deleted Successfully")))
                .andExpect(jsonPath("$.status", is("200")));
    }


}

