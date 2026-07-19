package com.anilyadav.ems.dto.response;

import com.anilyadav.ems.enums.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private Boolean enabled;

    private RoleType role;
}