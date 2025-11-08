# Stage 1: Build with Java 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run with Java 17
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/transportation_app_backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]
