package net.devgrr.springbootinit.dto;

import lombok.Data;
import net.devgrr.springbootinit.entity.Role;

@Data
public class UserCreateRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}