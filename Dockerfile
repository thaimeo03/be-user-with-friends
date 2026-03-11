# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies (this layer will be cached if pom.xml doesn't change)
COPY pom.xml .
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port (Spring Boot default is 8080)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.launch.JarLauncher health 2>/dev/null || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
