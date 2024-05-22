# Use Eclipse Temurin JDK 19 Alpine base image from the Docker Hub
FROM eclipse-temurin:19-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the /app directory
COPY ./target/CENG316_OIMS_Backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8081

# Set the entry point to run the jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
