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

/**
 * 인증 및 권한 테스트용 REST API 컨트롤러
 * 
 * JWT 토큰 기반 인증과 권한 부여가 정상적으로 작동하는지 테스트하기 위한 엔드포인트들을 제공합니다.
 * 각 엔드포인트는 서로 다른 권한 레벨을 요구하여 Spring Security 설정을 검증할 수 있습니다.
 * 
 * @SecurityRequirement: Swagger UI에서 JWT 토큰 인증이 필요함을 명시
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Test authentication and authorization")
@SecurityRequirement(name = "Bearer Authentication")
public class TestController {

    /**
     * 인증 상태 테스트 API
     * 
     * 현재 요청자의 인증 상태와 권한 정보를 반환합니다.
     * JWT 토큰이 올바르게 파싱되고 Authentication 객체가 생성되는지 확인할 수 있습니다.
     * 
     * @param authentication Spring Security에서 자동으로 주입되는 인증 정보 객체
     * @return ResponseEntity<Map<String, Object>> 인증 상태, 사용자명, 권한 목록을 포함한 응답
     */
    @GetMapping("/auth")
    @Operation(summary = "Test authentication", description = "Test if authentication is working")
    public ResponseEntity<Map<String, Object>> testAuth(Authentication authentication) {
        // 응답 데이터를 담을 Map 생성
        Map<String, Object> response = new HashMap<>();
        
        // 인증 상태 확인 (true/false)
        response.put("authenticated", authentication.isAuthenticated());
        
        // 인증된 사용자명 (JWT 토큰의 subject)
        response.put("username", authentication.getName());
        
        // 사용자의 권한 목록 (ROLE_USER, ROLE_ADMIN 등)
        response.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 권한 테스트 API
     * 
     * ADMIN 역할을 가진 사용자만 접근 가능한 엔드포인트입니다.
     * @PreAuthorize 어노테이션을 통해 메소드 레벨에서 권한을 검사합니다.
     * 
     * @PreAuthorize: Spring Security의 메소드 레벨 보안, 권한 검사를 수행
     * @return ResponseEntity<String> 접근 성공 메시지
     */
    @GetMapping("/admin")
    @Operation(summary = "Test admin authorization", description = "Test if admin authorization is working")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("Admin access granted");
    }

    /**
     * 일반 사용자 권한 테스트 API
     * 
     * 현재는 권한 검사가 주석 처리되어 있어 인증된 모든 사용자가 접근 가능합니다.
     * 필요시 @PreAuthorize("hasRole('USER')") 주석을 해제하여 USER 권한 검사를 활성화할 수 있습니다.
     * 
     * @return ResponseEntity<String> 접근 성공 메시지
     */
    @GetMapping("/user")
    @Operation(summary = "Test user authorization", description = "Test if user authorization is working")
//    @PreAuthorize("hasRole('USER')") // 주석 해제 시 USER 권한 필요
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("User access granted");
    }
}