# Multi-stage build for Spring Boot application
FROM gradle:8.14.2-jdk17 AS build

# Set working directory
WORKDIR /app

# Copy gradle wrapper and build files first (for better caching)
COPY gradle gradle
COPY gradlew gradlew.bat build.gradle settings.gradle ./

# Download dependencies (this layer will be cached if no dependency changes)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src src

# Build the application
RUN gradle bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Install curl for health checks (already included in jammy variant)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built jar file
COPY --from=build /app/build/libs/*.jar app.jar

# Create non-root user
RUN adduser --disabled-password --gecos '' appuser && chown appuser:appuser /app
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application with optimized JVM options
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]