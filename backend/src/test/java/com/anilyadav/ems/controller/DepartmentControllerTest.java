package com.anilyadav.ems.controller;

import com.anilyadav.ems.config.TestSecurityConfig;
import com.anilyadav.ems.dto.request.DepartmentRequest;
import com.anilyadav.ems.dto.response.DepartmentResponse;
import com.anilyadav.ems.service.auth.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(DepartmentController.class)
@Import(TestSecurityConfig.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DepartmentService departmentService;

    private DepartmentRequest getDepartmentRequest() {

        DepartmentRequest request = new DepartmentRequest();
        request.setName("Information Technology");
        request.setCode("IT");
        request.setDescription("IT Department");

        return request;
    }

    private DepartmentResponse getDepartmentResponse() {

        return DepartmentResponse.builder()
                .id(1L)
                .name("Information Technology")
                .code("IT")
                .description("IT Department")
                .build();
    }

    @Test
    @DisplayName("Should create department successfully")
    void createDepartment_ShouldReturn201() throws Exception {

        DepartmentRequest request = getDepartmentRequest();

        when(departmentService.createDepartment(any(DepartmentRequest.class)))
                .thenReturn(getDepartmentResponse());

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Information Technology"))
                .andExpect(jsonPath("$.code").value("IT"));
    }

    @Test
    @DisplayName("Should return department by id")
    void getDepartmentById_ShouldReturn200() throws Exception {

        when(departmentService.getDepartmentById(1L))
                .thenReturn(getDepartmentResponse());

        mockMvc.perform(get("/api/v1/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Information Technology"));
    }

    @Test
    @DisplayName("Should return all departments")
    void getAllDepartments_ShouldReturn200() throws Exception {

        when(departmentService.getAllDepartments())
                .thenReturn(List.of(getDepartmentResponse()));

        mockMvc.perform(get("/api/v1/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("IT"));
    }

    @Test
    @DisplayName("Should return department by code")
    void getDepartmentByCode_ShouldReturn200() throws Exception {

        when(departmentService.getDepartmentByCode("IT"))
                .thenReturn(getDepartmentResponse());

        mockMvc.perform(get("/api/v1/departments/code/IT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("IT"));
    }

    @Test
    @DisplayName("Should update department successfully")
    void updateDepartment_ShouldReturn200() throws Exception {

        DepartmentRequest request = getDepartmentRequest();

        when(departmentService.updateDepartment(eq(1L), any(DepartmentRequest.class)))
                .thenReturn(getDepartmentResponse());

        mockMvc.perform(put("/api/v1/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("IT Department"));
    }

    @Test
    @DisplayName("Should delete department successfully")
    void deleteDepartment_ShouldReturn204() throws Exception {

        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/v1/departments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void createDepartment_ShouldReturn400_WhenRequestIsInvalid() throws Exception {

        DepartmentRequest request = new DepartmentRequest();

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}