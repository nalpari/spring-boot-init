# Docker 배포 가이드

이 프로젝트는 Docker Compose를 사용하여 PostgreSQL 데이터베이스와 Spring Boot 애플리케이션을 함께 실행할 수 있습니다.

## 🚀 빠른 시작

### 1. 애플리케이션 시작
```bash
./docker-start.sh
```

### 2. 애플리케이션 중지
```bash
./docker-stop.sh
```

## 📋 서비스 구성

### 포트 매핑
- **Spring Boot App**: `http://localhost:8080`
- **PostgreSQL**: `localhost:5432`
- **Redis** (선택사항): `localhost:6379`

### 주요 엔드포인트
- **API 문서**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **API Docs**: http://localhost:8080/v3/api-docs

## 🐳 Docker 명령어

### 기본 명령어
```bash
# 백그라운드에서 시작
docker-compose up -d

# 이미지 재빌드와 함께 시작
docker-compose up --build

# 중지
docker-compose down

# 볼륨과 함께 완전 삭제
docker-compose down -v
```

### 로그 확인
```bash
# 모든 서비스 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f spring-boot-app
docker-compose logs -f postgres-db
```

### 컨테이너 상태 확인
```bash
# 실행 중인 컨테이너 확인
docker-compose ps

# 헬스체크 상태
docker-compose exec spring-boot-app curl http://localhost:8080/actuator/health
```

## 🗃️ 데이터베이스 접속

### psql 클라이언트 사용
```bash
# Docker 컨테이너를 통한 접속
docker-compose exec postgres-db psql -U mydb -d mydatabase

# 로컬 psql 클라이언트 사용
psql -h localhost -p 5432 -U mydb -d mydatabase
```

### 연결 정보
- **Host**: localhost
- **Port**: 5432
- **Database**: mydatabase
- **Username**: mydb
- **Password**: mypassword

## 🔧 환경 설정

### 환경 변수
Docker Compose 실행 시 다음 환경 변수를 설정할 수 있습니다:

```bash
export JWT_SECRET="your-secret-key"
export JWT_EXPIRATION="86400000"
```

### 설정 파일
- **로컬 개발**: `application.yml`
- **Docker 환경**: `application-docker.yml`

## 📁 디렉토리 구조

```
├── Dockerfile                 # Spring Boot 애플리케이션 이미지 빌드
├── docker-compose.yml        # 전체 스택 정의
├── docker-start.sh          # 시작 스크립트
├── docker-stop.sh           # 중지 스크립트
├── .dockerignore            # Docker 빌드 제외 파일
└── docker/
    └── init-scripts/
        └── 01-init.sql      # 데이터베이스 초기화 스크립트
```

## 🏥 헬스 체크

각 서비스는 헬스 체크를 제공합니다:

### Spring Boot
```bash
curl http://localhost:8080/actuator/health
```

### PostgreSQL
```bash
docker-compose exec postgres-db pg_isready -U mydb -d mydatabase
```

### Redis
```bash
docker-compose exec redis redis-cli ping
```

## 🔒 보안 고려사항

### 운영 환경에서는 다음을 변경하세요:
1. **데이터베이스 비밀번호** 변경
2. **JWT Secret** 강화
3. **외부 접근 포트** 제한
4. **SSL/TLS** 적용

### 예시 운영 환경 설정
```yaml
# docker-compose.prod.yml
environment:
  POSTGRES_PASSWORD: ${DB_PASSWORD}
  JWT_SECRET: ${JWT_SECRET}
  SPRING_PROFILES_ACTIVE: prod
```

## 🚨 문제 해결

### 일반적인 문제

#### 1. 포트 충돌
```bash
# 다른 서비스가 포트를 사용 중인 경우
sudo lsof -i :8080
sudo lsof -i :5432
```

#### 2. 권한 문제
```bash
# 스크립트 실행 권한 부여
chmod +x docker-start.sh docker-stop.sh
```

#### 3. 메모리 부족
```bash
# Docker 메모리 사용량 확인
docker stats
```

#### 4. 로그 확인
```bash
# 자세한 로그 확인
docker-compose logs --tail=100 spring-boot-app
```

## 🔄 개발 워크플로우

### 코드 변경 후 재배포
```bash
# 1. 애플리케이션만 재빌드
docker-compose up --build spring-boot-app

# 2. 또는 전체 재시작
./docker-stop.sh
./docker-start.sh
```

### 데이터베이스 스키마 변경
JPA의 `ddl-auto: update` 설정으로 자동으로 스키마가 업데이트됩니다.

### 새로운 의존성 추가
```bash
# build.gradle 수정 후
docker-compose build spring-boot-app
docker-compose up -d spring-boot-app
```

## 🎯 성능 최적화

### JVM 튜닝
`docker-compose.yml`에서 JAVA_OPTS 수정:
```yaml
environment:
  JAVA_OPTS: "-Xms1024m -Xmx2048m -XX:+UseG1GC"
```

### 데이터베이스 튜닝
PostgreSQL 설정을 위한 추가 볼륨 마운트:
```yaml
volumes:
  - ./docker/postgres.conf:/etc/postgresql/postgresql.conf
```