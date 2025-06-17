package net.devgrr.springbootinit.dto;

import lombok.Data;
import net.devgrr.springbootinit.entity.Role;

@Data
public class UserUpdateRequest {
    private String username;
    private String email;
    private Role role;
}