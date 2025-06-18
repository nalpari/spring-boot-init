#!/bin/bash

# Docker Compose 애플리케이션 중지 스크립트

echo "🛑 Spring Boot 애플리케이션을 중지합니다..."

# 컨테이너 중지 및 제거
docker-compose down

echo "🧹 사용하지 않는 Docker 리소스를 정리합니다..."

# 사용하지 않는 이미지 제거 (선택사항)
read -p "🗑️  사용하지 않는 Docker 이미지도 제거하시겠습니까? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker image prune -f
    echo "✅ 사용하지 않는 이미지가 제거되었습니다."
fi

# 볼륨 제거 확인 (주의: 데이터베이스 데이터가 삭제됩니다)
read -p "⚠️  데이터베이스 볼륨도 제거하시겠습니까? (데이터가 모두 삭제됩니다) (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker-compose down -v
    echo "🗑️  모든 볼륨이 제거되었습니다. (데이터베이스 데이터 포함)"
fi

echo "✅ 애플리케이션이 성공적으로 중지되었습니다."