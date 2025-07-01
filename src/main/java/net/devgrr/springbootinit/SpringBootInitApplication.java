package net.devgrr.springbootinit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Boot 초기화 메인 애플리케이션 클래스
 * 
 * @SpringBootApplication: Spring Boot의 자동 설정, 컴포넌트 스캔, 설정을 활성화하는 메타 어노테이션
 * @EnableJpaAuditing: JPA Auditing 기능을 활성화하여 Entity의 생성/수정 시간을 자동으로 관리
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@SpringBootApplication
@EnableJpaAuditing
public class SpringBootInitApplication {

    /**
     * Spring Boot 애플리케이션의 진입점(Entry Point)
     * 
     * @param args 명령행 인수 배열
     */
    public static void main(String[] args) {
        // Spring Boot 애플리케이션을 시작하고 ApplicationContext를 반환
        SpringApplication.run(SpringBootInitApplication.class, args);
    }

}
