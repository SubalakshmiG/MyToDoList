# Use an official Java 17 runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged executable JAR file into the container
COPY target/*.jar app.jar

# Command to execute the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]