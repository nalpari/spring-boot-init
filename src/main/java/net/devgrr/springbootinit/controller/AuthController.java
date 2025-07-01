package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.AuthResponse;
import net.devgrr.springbootinit.dto.LoginRequest;
import net.devgrr.springbootinit.dto.SignupRequest;
import net.devgrr.springbootinit.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 REST API 컨트롤러
 * 
 * 사용자 회원가입, 로그인 등 인증 관련 엔드포인트를 제공합니다.
 * JWT 토큰 기반 인증을 사용하며, 성공 시 토큰을 반환합니다.
 * 
 * @RestController: @Controller + @ResponseBody의 조합으로 RESTful 웹 서비스 컨트롤러 지정
 * @RequestMapping: 클래스 레벨에서 공통 URL 매핑 경로 설정 ("/api/auth")
 * @RequiredArgsConstructor: final 필드에 대한 생성자 자동 생성 (의존성 주입용)
 * @Tag: Swagger UI에서 API 그룹화를 위한 태그 설정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    /**
     * 인증 서비스 의존성 주입
     * final 키워드와 @RequiredArgsConstructor를 통해 생성자 주입 방식 사용
     */
    private final AuthService authService;

    /**
     * 사용자 회원가입 API
     * 
     * 새로운 사용자를 등록하고 JWT 토큰을 반환합니다.
     * 사용자명과 이메일의 중복 검사를 수행하며, 비밀번호는 BCrypt로 암호화됩니다.
     * 
     * @PostMapping: HTTP POST 요청을 처리하는 핸들러 메소드 지정
     * @Operation: Swagger UI에서 표시될 API 설명 정보
     * @ApiResponses: 가능한 HTTP 응답 코드와 설명 정의
     * @RequestBody: HTTP 요청 본문을 Java 객체로 변환
     * 
     * @param request 회원가입 요청 데이터 (사용자명, 이메일, 비밀번호)
     * @return ResponseEntity<AuthResponse> JWT 토큰과 사용자 정보를 포함한 응답
     */
    @PostMapping("/signup")
    @Operation(summary = "User signup", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        // 회원가입 서비스 호출하여 사용자 등록 및 JWT 토큰 생성
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 로그인 API
     * 
     * 사용자명/이메일과 비밀번호를 검증하여 JWT 토큰을 발급합니다.
     * Spring Security의 AuthenticationManager를 통해 인증을 처리합니다.
     * 
     * @param request 로그인 요청 데이터 (사용자명/이메일, 비밀번호)
     * @return ResponseEntity<AuthResponse> JWT 토큰과 사용자 정보를 포함한 응답
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 로그인 서비스 호출하여 인증 처리 및 JWT 토큰 생성
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}