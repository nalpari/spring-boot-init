package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.UserCreateRequest;
import net.devgrr.springbootinit.dto.UserDto;
import net.devgrr.springbootinit.dto.UserUpdateRequest;
import net.devgrr.springbootinit.entity.Role;
import net.devgrr.springbootinit.entity.User;
import net.devgrr.springbootinit.exception.UserAlreadyExistsException;
import net.devgrr.springbootinit.exception.UserNotFoundException;
import net.devgrr.springbootinit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;
    private UserCreateRequest testCreateRequest;
    private UserUpdateRequest testUpdateRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        testUserDto = UserDto.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role(Role.USER)
                .build();

        testCreateRequest = new UserCreateRequest();
        testCreateRequest.setUsername("newuser");
        testCreateRequest.setEmail("new@example.com");
        testCreateRequest.setPassword("password123");
        testCreateRequest.setRole(Role.USER);

        testUpdateRequest = new UserUpdateRequest();
        testUpdateRequest.setUsername("updateduser");
        testUpdateRequest.setEmail("updated@example.com");
        testUpdateRequest.setRole(Role.ADMIN);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .email("user2@example.com")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        assertThat(result.get(1).getUsername()).isEqualTo("user2");
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsersWithPageable_shouldReturnPagedUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(Arrays.asList(testUser));

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserDto> result = userService.getAllUsers(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository).findAll(pageable);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUserById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenUserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUserByUsername("testuser");

        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getUserByUsername_shouldThrowException_whenUserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByUsername("nonexistent"))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findByUsername("nonexistent");
    }

    @Test
    void getUsersByRole_shouldReturnUsersWithRole() {
        when(userRepository.findByRole(Role.USER)).thenReturn(Arrays.asList(testUser));

        List<UserDto> result = userService.getUsersByRole(Role.USER);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRole()).isEqualTo(Role.USER);
        verify(userRepository).findByRole(Role.USER);
    }

    @Test
    void searchUsers_shouldReturnMatchingUsers() {
        when(userRepository.searchByKeyword("test")).thenReturn(Arrays.asList(testUser));

        List<UserDto> result = userService.searchUsers("test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository).searchByKeyword("test");
    }

    @Test
    void createUser_shouldCreateNewUser_whenValidRequest() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.createUser(testCreateRequest);

        assertThat(result).isNotNull();
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByEmail("new@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(testCreateRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("Username already exists");
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowException_whenEmailExists() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(testCreateRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("Email already exists");
        verify(userRepository).existsByEmail("new@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_shouldSetDefaultRole_whenRoleNotProvided() {
        testCreateRequest.setRole(null);
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.createUser(testCreateRequest);

        assertThat(result).isNotNull();
        verify(userRepository).save(argThat(user -> user.getRole() == Role.USER));
    }

    @Test
    void updateUser_shouldUpdateUser_whenValidRequest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByUsernameAndIdNot("updateduser", 1L)).thenReturn(false);
        when(userRepository.existsByEmailAndIdNot("updated@example.com", 1L)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.updateUser(1L, testUpdateRequest);

        assertThat(result).isNotNull();
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(1L, testUpdateRequest))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_shouldThrowException_whenUsernameAlreadyExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByUsernameAndIdNot("updateduser", 1L)).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(1L, testUpdateRequest))
                .isInstanceOf(UserAlreadyExistsException.class);
        verify(userRepository).findById(1L);
        verify(userRepository).existsByUsernameAndIdNot("updateduser", 1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_shouldThrowException_whenEmailAlreadyExists() {
        testUpdateRequest.setUsername(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmailAndIdNot("updated@example.com", 1L)).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(1L, testUpdateRequest))
                .isInstanceOf(UserAlreadyExistsException.class);
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailAndIdNot("updated@example.com", 1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_shouldNotUpdateUsername_whenSameAsExisting() {
        testUpdateRequest.setUsername("testuser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmailAndIdNot("updated@example.com", 1L)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.updateUser(1L, testUpdateRequest);

        assertThat(result).isNotNull();
        verify(userRepository, never()).existsByUsernameAndIdNot(anyString(), anyLong());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(UserNotFoundException.class);
        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}