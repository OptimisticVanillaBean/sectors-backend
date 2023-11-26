package com.sectors.sectorsbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    @NotEmpty
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
