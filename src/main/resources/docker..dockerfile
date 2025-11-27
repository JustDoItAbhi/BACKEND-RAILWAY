# Use Eclipse Temurin JDK 17
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean install -DskipTests

# Expose application port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "target/userService-0.0.1-SNAPSHOT.jar"]