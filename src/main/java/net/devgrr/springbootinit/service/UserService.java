package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.UserCreateRequest;
import net.devgrr.springbootinit.dto.UserDto;
import net.devgrr.springbootinit.dto.UserUpdateRequest;
import net.devgrr.springbootinit.entity.Role;
import net.devgrr.springbootinit.entity.User;
import net.devgrr.springbootinit.exception.UserAlreadyExistsException;
import net.devgrr.springbootinit.exception.UserNotFoundException;
import net.devgrr.springbootinit.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 관리 비즈니스 로직을 처리하는 서비스 클래스
 * 
 * 사용자의 CRUD 작업, 검색, 페이징 등의 비즈니스 로직을 담당합니다.
 * 트랜잭션 관리를 통해 데이터 일관성을 보장하며, DTO 변환을 통해 계층 간 데이터 전송을 최적화합니다.
 * 
 * @Service: Spring의 서비스 계층 컴포넌트임을 나타내는 어노테이션
 * @RequiredArgsConstructor: final 필드에 대한 생성자 자동 생성
 * @Transactional: 클래스 레벨의 트랜잭션 설정 (기본: 읽기-쓰기)
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    /**
     * 사용자 데이터 접근을 위한 Repository
     */
    private final UserRepository userRepository;
    
    /**
     * 비밀번호 암호화를 위한 Encoder (BCrypt 사용)
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 모든 사용자 목록을 조회합니다.
     * 
     * @Transactional(readOnly = true): 읽기 전용 트랜잭션 설정으로 성능 최적화
     * @return List<UserDto> 모든 사용자 정보의 DTO 목록
     */
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 페이징된 사용자 목록을 조회합니다.
     * 
     * @param pageable 페이징 정보 (페이지 번호, 사이즈, 정렬 조건)
     * @return Page<UserDto> 페이징된 사용자 정보의 DTO
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    /**
     * ID로 특정 사용자를 조회합니다.
     * 
     * @param id 조회할 사용자의 고유 ID
     * @return UserDto 사용자 정보 DTO
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToDto(user);
    }

    /**
     * 사용자명으로 특정 사용자를 조회합니다.
     * 
     * @param username 조회할 사용자명
     * @return UserDto 사용자 정보 DTO
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("username", username));
        return convertToDto(user);
    }

    /**
     * 특정 역할을 가진 사용자들을 조회합니다.
     * 
     * @param role 조회할 사용자 역할 (USER 또는 ADMIN)
     * @return List<UserDto> 해당 역할을 가진 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 키워드로 사용자를 검색합니다.
     * 사용자명 또는 이메일에 키워드가 포함된 사용자들을 반환합니다.
     * 
     * @param keyword 검색할 키워드
     * @return List<UserDto> 키워드가 포함된 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<UserDto> searchUsers(String keyword) {
        return userRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 새로운 사용자를 생성합니다.
     * 
     * 사용자명과 이메일의 중복을 검사하고, 비밀번호를 암호화하여 새 사용자를 저장합니다.
     * 역할이 지정되지 않은 경우 기본값으로 USER 역할을 부여합니다.
     * 
     * @param request 사용자 생성 요청 정보
     * @return UserDto 생성된 사용자 정보 DTO
     * @throws UserAlreadyExistsException 사용자명 또는 이메일이 이미 존재할 경우
     */
    public UserDto createUser(UserCreateRequest request) {
        // 사용자명 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        
        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        // 새 사용자 엔티티 생성
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .role(request.getRole() != null ? request.getRole() : Role.USER) // 기본 역할: USER
                .build();

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    /**
     * 기존 사용자 정보를 수정합니다.
     * 
     * 수정할 필드만 업데이트하며, 사용자명과 이메일 변경 시 중복을 검사합니다.
     * null이 아닌 필드만 업데이트하는 부분 업데이트를 지원합니다.
     * 
     * @param id 수정할 사용자의 고유 ID
     * @param request 사용자 수정 요청 정보
     * @return UserDto 수정된 사용자 정보 DTO
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우
     * @throws UserAlreadyExistsException 변경하려는 사용자명 또는 이메일이 이미 존재할 경우
     */
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // 사용자명 변경 시 중복 검사 (본인 제외)
        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsernameAndIdNot(request.getUsername(), id)) {
                throw new UserAlreadyExistsException(request.getUsername());
            }
            user.setUsername(request.getUsername());
        }

        // 이메일 변경 시 중복 검사 (본인 제외)
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        // 역할 변경
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    /**
     * 사용자를 삭제합니다.
     * 
     * @param id 삭제할 사용자의 고유 ID
     * @throws UserNotFoundException 사용자가 존재하지 않을 경우
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * User 엔티티를 UserDto로 변환합니다.
     * 
     * 내부적으로 사용되는 헬퍼 메소드로, 엔티티의 필요한 필드만을 DTO에 복사합니다.
     * 비밀번호와 같은 민감한 정보는 제외하고 변환합니다.
     * 
     * @param user 변환할 User 엔티티
     * @return UserDto 변환된 사용자 정보 DTO
     */
    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}