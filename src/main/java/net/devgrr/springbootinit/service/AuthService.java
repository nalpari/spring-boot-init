package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.AuthResponse;
import net.devgrr.springbootinit.dto.LoginRequest;
import net.devgrr.springbootinit.dto.SignupRequest;
import net.devgrr.springbootinit.entity.Role;
import net.devgrr.springbootinit.entity.User;
import net.devgrr.springbootinit.exception.UserAlreadyExistsException;
import net.devgrr.springbootinit.exception.UserNotFoundException;
import net.devgrr.springbootinit.repository.UserRepository;
import net.devgrr.springbootinit.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 
 * 사용자 회원가입, 로그인, JWT 토큰 생성 등의 인증 관련 기능을 제공합니다.
 * Spring Security와 연동하여 안전한 인증 처리를 수행합니다.
 * 
 * @Service: Spring의 서비스 계층 컴포넌트임을 나타내는 어노테이션
 * @RequiredArgsConstructor: final 필드에 대한 생성자 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * 사용자 데이터 접근을 위한 Repository
     */
    private final UserRepository userRepository;
    
    /**
     * 비밀번호 암호화를 위한 Encoder (BCrypt 사용)
     */
    private final PasswordEncoder passwordEncoder;
    
    /**
     * JWT 토큰 생성 및 검증을 위한 유틸리티
     */
    private final JwtUtil jwtUtil;
    
    /**
     * Spring Security 인증 관리자
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 사용자 회원가입 처리
     * 
     * 새로운 사용자를 등록하고 JWT 토큰을 발급합니다.
     * 사용자명과 이메일의 중복을 검사하고, 비밀번호를 암호화하여 저장합니다.
     * 새로 가입한 사용자는 기본적으로 USER 권한을 부여받습니다.
     * 
     * @param request 회원가입 요청 정보 (사용자명, 이메일, 비밀번호)
     * @return AuthResponse JWT 토큰과 사용자 정보를 포함한 응답
     * @throws UserAlreadyExistsException 사용자명 또는 이메일이 이미 존재하는 경우
     */
    public AuthResponse signup(SignupRequest request) {
        // 사용자명 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        
        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        // 새 사용자 객체 생성 (빌더 패턴 사용)
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 BCrypt 암호화
                .role(Role.USER) // 기본 권한은 USER
                .build();

        // 사용자 정보 데이터베이스에 저장
        userRepository.save(user);
        
        // JWT 토큰 생성
        String token = jwtUtil.generateToken(user);

        // 인증 응답 반환 (토큰, 사용자명, 이메일)
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

    /**
     * 사용자 로그인 처리
     * 
     * 사용자명/이메일과 비밀번호를 검증하여 JWT 토큰을 발급합니다.
     * Spring Security의 AuthenticationManager를 통해 인증을 수행합니다.
     * 
     * @param request 로그인 요청 정보 (사용자명/이메일, 비밀번호)
     * @return AuthResponse JWT 토큰과 사용자 정보를 포함한 응답
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     * @throws org.springframework.security.core.AuthenticationException 인증 실패 시
     */
    public AuthResponse login(LoginRequest request) {
        // Spring Security AuthenticationManager를 통한 인증 처리
        // 인증 실패 시 AuthenticationException 발생
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 인증 성공 후 사용자 정보 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("username", request.getUsername()));

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(user);

        // 인증 응답 반환 (토큰, 사용자명, 이메일)
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }
}