# -------- Stage 1: Build with Maven and Java 21 --------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copy all files and build the application
COPY . .
RUN mvn clean package -DskipTests

# -------- Stage 2: Runtime with minimal Java 21 JDK --------
FROM eclipse-temurin:21
WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/transportation_app_backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port defined in application.properties
EXPOSE 5000

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
