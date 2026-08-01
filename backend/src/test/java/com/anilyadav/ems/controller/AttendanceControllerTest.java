package com.anilyadav.ems.controller;

import com.anilyadav.ems.dto.request.AttendanceRequest;
import com.anilyadav.ems.dto.response.AttendanceResponse;
import com.anilyadav.ems.enums.AttendanceStatus;
import com.anilyadav.ems.service.auth.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AttendanceService attendanceService;

    private AttendanceRequest getAttendanceRequest() {
        return AttendanceRequest.builder()
                .employeeId(1L)
                .attendanceDate(LocalDate.of(2025, 1, 15))
                .checkInTime(LocalTime.of(9, 0))
                .checkOutTime(LocalTime.of(18, 0))
                .status(AttendanceStatus.PRESENT)
                .build();
    }

    private AttendanceResponse getAttendanceResponse() {
        return AttendanceResponse.builder()
                .id(1L)
                .employeeId(1L)
                .employeeCode("EMP001")
                .employeeName("John Doe")
                .attendanceDate(LocalDate.of(2025, 1, 15))
                .checkInTime(LocalTime.of(9, 0))
                .checkOutTime(LocalTime.of(18, 0))
                .status(AttendanceStatus.PRESENT)
                .workingHours(9.0)
                .build();
    }

    @Test
    @DisplayName("Should mark attendance successfully")
    void markAttendance_ShouldReturn201() throws Exception {

        when(attendanceService.markAttendance(any(AttendanceRequest.class)))
                .thenReturn(getAttendanceResponse());

        mockMvc.perform(post("/api/v1/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getAttendanceRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.employeeCode").value("EMP001"))
                .andExpect(jsonPath("$.employeeName").value("John Doe"));
    }

    @Test
    @DisplayName("Should return attendance by id")
    void getAttendanceById_ShouldReturn200() throws Exception {

        when(attendanceService.getAttendanceById(1L))
                .thenReturn(getAttendanceResponse());

        mockMvc.perform(get("/api/v1/attendance/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Should return all attendance")
    void getAllAttendance_ShouldReturn200() throws Exception {

        when(attendanceService.getAllAttendance())
                .thenReturn(List.of(getAttendanceResponse()));

        mockMvc.perform(get("/api/v1/attendance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeCode").value("EMP001"));
    }

    @Test
    @DisplayName("Should return attendance by employee")
    void getAttendanceByEmployee_ShouldReturn200() throws Exception {

        when(attendanceService.getAttendanceByEmployee(1L))
                .thenReturn(List.of(getAttendanceResponse()));

        mockMvc.perform(get("/api/v1/attendance/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

    @Test
    @DisplayName("Should return attendance by date")
    void getAttendanceByDate_ShouldReturn200() throws Exception {

        LocalDate date = LocalDate.of(2025, 1, 15);

        when(attendanceService.getAttendanceByDate(date))
                .thenReturn(List.of(getAttendanceResponse()));

        mockMvc.perform(get("/api/v1/attendance/date/2025-01-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].attendanceDate").value("2025-01-15"));
    }

    @Test
    @DisplayName("Should update attendance successfully")
    void updateAttendance_ShouldReturn200() throws Exception {

        when(attendanceService.updateAttendance(eq(1L), any(AttendanceRequest.class)))
                .thenReturn(getAttendanceResponse());

        mockMvc.perform(put("/api/v1/attendance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getAttendanceRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workingHours").value(9.0));
    }

    @Test
    @DisplayName("Should delete attendance successfully")
    void deleteAttendance_ShouldReturn204() throws Exception {

        doNothing().when(attendanceService).deleteAttendance(1L);

        mockMvc.perform(delete("/api/v1/attendance/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void markAttendance_ShouldReturn400_WhenRequestIsInvalid() throws Exception {

        AttendanceRequest request = new AttendanceRequest();

        mockMvc.perform(post("/api/v1/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}