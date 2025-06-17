package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Test authentication and authorization")
@SecurityRequirement(name = "Bearer Authentication")
public class TestController {

    @GetMapping("/auth")
    @Operation(summary = "Test authentication", description = "Test if authentication is working")
    public ResponseEntity<Map<String, Object>> testAuth(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authentication.isAuthenticated());
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    @Operation(summary = "Test admin authorization", description = "Test if admin authorization is working")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("Admin access granted");
    }

    @GetMapping("/user")
    @Operation(summary = "Test user authorization", description = "Test if user authorization is working")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("User access granted");
    }
}