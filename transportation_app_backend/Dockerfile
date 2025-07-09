# -------- Stage 1: Build the app using Maven --------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# -------- Stage 2: Run the app with Java 17 --------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Set the port your Spring Boot app uses
EXPOSE 5000

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
