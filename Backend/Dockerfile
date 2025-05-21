# Stage 1: Build the application
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port used by the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]