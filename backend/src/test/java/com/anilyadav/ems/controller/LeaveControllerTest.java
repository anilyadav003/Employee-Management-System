package com.anilyadav.ems.controller;

import com.anilyadav.ems.config.TestSecurityConfig;
import com.anilyadav.ems.dto.request.LeaveRequest;
import com.anilyadav.ems.dto.response.LeaveResponse;
import com.anilyadav.ems.enums.LeaveStatus;
import com.anilyadav.ems.service.auth.LeaveService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LeaveController.class)
@Import(TestSecurityConfig.class)
class LeaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LeaveService leaveService;

    private LeaveRequest getLeaveRequest() {

        LeaveRequest request = new LeaveRequest();
        request.setEmployeeId(1L);
        request.setStartDate(LocalDate.now().plusDays(1));
        request.setEndDate(LocalDate.now().plusDays(3));
        request.setReason("Medical Leave");

        return request;
    }

    private LeaveResponse getLeaveResponse() {

        LeaveResponse response = new LeaveResponse();
        response.setId(1L);
        response.setEmployeeId(1L);
        response.setEmployeeCode("EMP001");
        response.setEmployeeName("John Doe");
        response.setStartDate(LocalDate.now().plusDays(1));
        response.setEndDate(LocalDate.now().plusDays(3));
        response.setReason("Medical Leave");
        response.setStatus(LeaveStatus.PENDING);

        return response;
    }

    @Test
    @DisplayName("Should apply leave successfully")
    void applyLeave_ShouldReturn201() throws Exception {

        when(leaveService.applyLeave(any(LeaveRequest.class)))
                .thenReturn(getLeaveResponse());

        mockMvc.perform(post("/api/v1/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLeaveRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeCode").value("EMP001"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("Should return leave by id")
    void getLeaveById_ShouldReturn200() throws Exception {

        when(leaveService.getLeaveById(1L))
                .thenReturn(getLeaveResponse());

        mockMvc.perform(get("/api/v1/leaves/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Should return all leaves")
    void getAllLeaves_ShouldReturn200() throws Exception {

        when(leaveService.getAllLeaves())
                .thenReturn(List.of(getLeaveResponse()));

        mockMvc.perform(get("/api/v1/leaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeCode").value("EMP001"));
    }

    @Test
    @DisplayName("Should return leaves by employee")
    void getLeavesByEmployee_ShouldReturn200() throws Exception {

        when(leaveService.getLeavesByEmployee(1L))
                .thenReturn(List.of(getLeaveResponse()));

        mockMvc.perform(get("/api/v1/leaves/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

    @Test
    @DisplayName("Should return leaves by status")
    void getLeavesByStatus_ShouldReturn200() throws Exception {

        when(leaveService.getLeavesByStatus(LeaveStatus.PENDING))
                .thenReturn(List.of(getLeaveResponse()));

        mockMvc.perform(get("/api/v1/leaves/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    @DisplayName("Should update leave successfully")
    void updateLeave_ShouldReturn200() throws Exception {

        when(leaveService.updateLeave(eq(1L), any(LeaveRequest.class)))
                .thenReturn(getLeaveResponse());

        mockMvc.perform(put("/api/v1/leaves/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLeaveRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Medical Leave"));
    }

    @Test
    @DisplayName("Should update leave status successfully")
    void updateLeaveStatus_ShouldReturn200() throws Exception {

        LeaveResponse response = getLeaveResponse();
        response.setStatus(LeaveStatus.APPROVED);

        when(leaveService.updateLeaveStatus(1L, LeaveStatus.APPROVED))
                .thenReturn(response);

        mockMvc.perform(patch("/api/v1/leaves/1/status/APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    @DisplayName("Should delete leave successfully")
    void deleteLeave_ShouldReturn204() throws Exception {

        doNothing().when(leaveService).deleteLeave(1L);

        mockMvc.perform(delete("/api/v1/leaves/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void applyLeave_ShouldReturn400_WhenRequestIsInvalid() throws Exception {

        LeaveRequest request = new LeaveRequest();

        mockMvc.perform(post("/api/v1/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}