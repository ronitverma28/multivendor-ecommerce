# Use lightweight Java 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give executable permission to mvnw
RUN chmod +x mvnw

# Download dependencies separately (better Docker caching)
RUN ./mvnw dependency:go-offline

# Copy remaining project files
COPY src src

# Copy .jar file
COPY target/*.jar app.jar

# Build Spring Boot application
RUN ./mvnw clean package -DskipTests

# Expose application port
EXPOSE 8080

# Run Spring Boot jar
CMD ["java", "-jar", "app.jar"]