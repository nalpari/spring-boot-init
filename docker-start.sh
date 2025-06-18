#!/bin/bash

# Docker Compose를 사용한 Spring Boot 애플리케이션 실행 스크립트

echo "🚀 Spring Boot 애플리케이션을 Docker로 시작합니다..."

# 환경 변수 설정 (필요시 수정)
export JWT_SECRET="myVerySecretKeyForJWTTokenGeneration123456789"
export JWT_EXPIRATION="86400000"

# 기존 컨테이너 정리
echo "🧹 기존 컨테이너를 정리합니다..."
docker-compose down

# 이미지 빌드 및 컨테이너 시작
echo "🔨 이미지를 빌드하고 컨테이너를 시작합니다..."
docker-compose up --build -d

# 컨테이너 상태 확인
echo "📋 컨테이너 상태를 확인합니다..."
docker-compose ps

# 로그 확인
echo "📝 애플리케이션 로그를 확인합니다 (Ctrl+C로 종료)..."
echo "💡 백그라운드에서 실행하려면 'docker-compose logs -f spring-boot-app' 사용"
sleep 5

# 헬스체크 대기
echo "⏳ 애플리케이션이 시작될 때까지 대기합니다..."
for i in {1..30}; do
    if curl -f http://localhost:8080/actuator/health >/dev/null 2>&1; then
        echo "✅ 애플리케이션이 정상적으로 시작되었습니다!"
        echo ""
        echo "🌐 접속 정보:"
        echo "   - API: http://localhost:8080"
        echo "   - Swagger UI: http://localhost:8080/swagger-ui.html"
        echo "   - Health Check: http://localhost:8080/actuator/health"
        echo "   - Database: localhost:5432 (mydb/mypassword)"
        echo ""
        echo "📋 유용한 명령어:"
        echo "   - 로그 확인: docker-compose logs -f spring-boot-app"
        echo "   - 컨테이너 중지: docker-compose down"
        echo "   - 데이터베이스 접속: psql -h localhost -U mydb -d mydatabase"
        break
    fi
    echo "⏳ 애플리케이션 시작 대기 중... ($i/30)"
    sleep 2
done

if [ $i -eq 30 ]; then
    echo "❌ 애플리케이션 시작에 실패했습니다. 로그를 확인해주세요:"
    echo "   docker-compose logs spring-boot-app"
fi