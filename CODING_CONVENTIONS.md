# 코딩 컨벤션 (Coding Conventions)

## 1. 일반 원칙

### 1.1 언어 및 프레임워크
- **Java 17** 사용
- **Spring Boot 3.5.0** 기반
- **Lombok** 활용으로 보일러플레이트 코드 최소화

### 1.2 코드 스타일
- **들여쓰기**: 4칸 공백 사용
- **줄 길이**: 최대 120자
- **인코딩**: UTF-8

## 2. 패키지 구조

```
net.devgrr.springbootinit
├── config/          # 설정 클래스
├── controller/      # REST 컨트롤러
├── dto/            # 데이터 전송 객체
├── entity/         # JPA 엔티티
├── exception/      # 커스텀 예외
├── filter/         # 필터 클래스
├── repository/     # 데이터 접근 계층
├── service/        # 비즈니스 로직
└── util/          # 유틸리티 클래스
```

## 3. 명명 규칙

### 3.1 클래스명
- **PascalCase** 사용
- 역할에 따른 접미사 사용:
  - Controller: `~Controller`
  - Service: `~Service`
  - Repository: `~Repository`
  - Entity: 단수형 명사
  - DTO: `~Dto` 또는 `~Request`/`~Response`
  - Exception: `~Exception`

### 3.2 메서드명
- **camelCase** 사용
- 동사로 시작
- CRUD 작업 명명:
  - 조회: `get~`, `find~`, `search~`
  - 생성: `create~`
  - 수정: `update~`
  - 삭제: `delete~`

### 3.3 변수명
- **camelCase** 사용
- 의미있는 이름 사용
- Boolean 변수: `is~`, `has~` 접두사

## 4. 어노테이션 사용

### 4.1 Lombok 어노테이션
```java
// Entity
@Entity
@Table(name = "table_name")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

// DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// Service/Controller
@RequiredArgsConstructor
```

### 4.2 Spring 어노테이션
```java
// Controller
@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
@Tag(name = "Resource Management", description = "Resource CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")

// Service
@Service
@RequiredArgsConstructor
@Transactional
```

## 5. 엔티티 설계

### 5.1 기본 구조
```java
@Entity
@Table(name = "entities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Entity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

### 5.2 관계 매핑
- **FetchType.LAZY** 기본 사용
- `@Builder.Default`로 컬렉션 초기화
- 양방향 관계 시 `mappedBy` 사용

## 6. 컨트롤러 설계

### 6.1 기본 구조
```java
@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
@Tag(name = "Resource Management")
@SecurityRequirement(name = "Bearer Authentication")
public class ResourceController {
    
    private final ResourceService resourceService;
    
    @GetMapping
    @Operation(summary = "Get all resources")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success")
    })
    public ResponseEntity<List<ResourceDto>> getAllResources() {
        // implementation
    }
}
```

### 6.2 HTTP 메서드 매핑
- `@GetMapping`: 조회
- `@PostMapping`: 생성
- `@PutMapping`: 전체 수정
- `@PatchMapping`: 부분 수정
- `@DeleteMapping`: 삭제

### 6.3 응답 상태 코드
- 성공 조회: `200 OK`
- 성공 생성: `201 CREATED`
- 성공 수정: `200 OK`
- 성공 삭제: `204 NO_CONTENT`

## 7. 서비스 레이어

### 7.1 트랜잭션 관리
```java
@Service
@RequiredArgsConstructor
@Transactional  // 클래스 레벨에 설정
public class ResourceService {
    
    @Transactional(readOnly = true)  // 조회 메서드
    public ResourceDto getResource(Long id) {
        // implementation
    }
    
    public ResourceDto createResource(ResourceCreateRequest request) {
        // 기본적으로 readOnly = false
    }
}
```

### 7.2 예외 처리
- 도메인별 커스텀 예외 생성
- `~NotFoundException` 패턴 사용
- 예외 메시지에 구체적인 정보 포함

### 7.3 DTO 변환
- `convertToDto()` private 메서드 사용
- Builder 패턴으로 객체 생성

## 8. 테스트 코드

### 8.1 테스트 클래스 구조
```java
@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {
    
    @Mock
    private ResourceRepository resourceRepository;
    
    @InjectMocks
    private ResourceService resourceService;
    
    @BeforeEach
    void setUp() {
        // 테스트 데이터 초기화
    }
    
    @Test
    void methodName_shouldExpectedBehavior_whenCondition() {
        // given
        // when
        // then
    }
}
```

### 8.2 테스트 메서드 명명
- `methodName_shouldExpectedBehavior_whenCondition` 패턴
- 한국어 설명은 `@DisplayName` 사용 가능

### 8.3 Assertion 라이브러리
- **AssertJ** 사용 (`assertThat()`)
- **Mockito** 사용 (`when()`, `verify()`)

## 9. 데이터베이스 설계

### 9.1 테이블명
- **소문자 + 언더스코어** 사용
- 복수형 사용 (`categories`, `products`)

### 9.2 컬럼명
- **소문자 + 언더스코어** 사용
- Boolean 컬럼: `is_` 접두사

### 9.3 공통 컬럼
```sql
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
is_active BOOLEAN NOT NULL DEFAULT true
```

## 10. API 문서화

### 10.1 Swagger 어노테이션 사용
```java
@Operation(summary = "간단한 설명", description = "상세한 설명")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "성공"),
    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
})
@Parameter(description = "파라미터 설명")
```

### 10.2 태그 및 보안 설정
- `@Tag(name = "리소스 관리", description = "설명")`
- `@SecurityRequirement(name = "Bearer Authentication")`

## 11. 보안

### 11.1 인증/인가
- JWT 토큰 기반 인증
- `@PreAuthorize` 어노테이션 사용
- 관리자 권한: `@PreAuthorize("hasRole('ADMIN')")`

### 11.2 입력 검증
- Bean Validation 사용 (`@Valid`, `@NotNull`, `@NotBlank`)
- 커스텀 검증 로직은 서비스 레이어에서 처리

## 12. 코드 품질

### 12.1 정적 분석
- 빌드 시 테스트 실행 필수
- 코드 커버리지 모니터링

### 12.2 코드 리뷰
- Pull Request 기반 개발
- 컨벤션 준수 여부 확인
- 보안 취약점 검토

## 13. Git 커밋 규칙

### 13.1 커밋 메시지 형식
```
타입: 간단한 설명

상세한 설명 (선택사항)
```

### 13.2 타입 분류
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `refactor`: 코드 리팩토링
- `test`: 테스트 추가/수정
- `docs`: 문서 수정
- `style`: 코드 스타일 변경

이 컨벤션은 프로젝트의 일관성과 유지보수성을 높이기 위한 가이드라인입니다. 팀원 모두가 숙지하고 준수해야 합니다.