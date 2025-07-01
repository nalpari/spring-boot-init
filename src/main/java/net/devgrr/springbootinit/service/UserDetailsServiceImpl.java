package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security UserDetailsService 구현 클래스
 * 
 * Spring Security가 사용자 인증 시 사용자 정보를 로드하기 위해 호출하는 서비스입니다.
 * 데이터베이스에서 사용자 정보를 조회하여 UserDetails 객체로 반환합니다.
 * User 엔티티가 UserDetails 인터페이스를 구현하므로 직접 반환 가능합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 사용자 데이터 접근을 위한 Repository
     */
    private final UserRepository userRepository;

    /**
     * 사용자명으로 사용자 정보를 조회하는 메소드
     * 
     * Spring Security가 인증 과정에서 호출하는 메소드입니다.
     * 사용자명을 기준으로 데이터베이스에서 사용자 정보를 조회합니다.
     * 
     * @param username 조회할 사용자명
     * @return UserDetails 사용자 정보 객체 (User 엔티티가 UserDetails 구현)
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자명으로 사용자 조회, 없으면 UsernameNotFoundException 발생
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}