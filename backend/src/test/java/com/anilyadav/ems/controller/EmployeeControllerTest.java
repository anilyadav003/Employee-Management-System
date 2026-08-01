package com.anilyadav.ems.controller;

import com.anilyadav.ems.config.TestSecurityConfig;
import com.anilyadav.ems.dto.request.EmployeeRequest;
import com.anilyadav.ems.dto.response.EmployeeResponse;
import com.anilyadav.ems.service.auth.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;


@WebMvcTest(EmployeeController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    private EmployeeRequest getEmployeeRequest() {

        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmployeeCode("EMP001");
        request.setDateOfJoining(LocalDate.of(2025, 1, 1));
        request.setDesignation("Software Engineer");
        request.setSalary(75000.0);
        request.setDepartmentId(1L);
        request.setUserId(1L);

        return request;
    }

    private EmployeeResponse getEmployeeResponse() {

        return EmployeeResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .employeeCode("EMP001")
                .dateOfJoining(LocalDate.of(2025, 1, 1))
                .designation("Software Engineer")
                .salary(75000.0)
                .departmentId(1L)
                .departmentName("IT")
                .userId(1L)
                .username("john")
                .build();
    }

    @Test
    @DisplayName("Should create employee successfully")
    void createEmployee_ShouldReturn201() throws Exception {

        EmployeeRequest request = getEmployeeRequest();

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(getEmployeeResponse());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.employeeCode").value("EMP001"));
    }

    @Test
    @DisplayName("Should return employee by id")
    void getEmployeeById_ShouldReturn200() throws Exception {

        when(employeeService.getEmployeeById(1L))
                .thenReturn(getEmployeeResponse());

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("Should return all employees")
    void getAllEmployees_ShouldReturn200() throws Exception {

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(getEmployeeResponse()));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeCode").value("EMP001"));
    }

    @Test
    @DisplayName("Should return employee by employee code")
    void getEmployeeByCode_ShouldReturn200() throws Exception {

        when(employeeService.getEmployeeByCode("EMP001"))
                .thenReturn(getEmployeeResponse());

        mockMvc.perform(get("/api/v1/employees/code/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCode").value("EMP001"));
    }

    @Test
    @DisplayName("Should update employee successfully")
    void updateEmployee_ShouldReturn200() throws Exception {

        EmployeeRequest request = getEmployeeRequest();

        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequest.class)))
                .thenReturn(getEmployeeResponse());

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Software Engineer"));
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void deleteEmployee_ShouldReturn204() throws Exception {

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void createEmployee_ShouldReturn400_WhenRequestIsInvalid() throws Exception {

        EmployeeRequest request = new EmployeeRequest();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}