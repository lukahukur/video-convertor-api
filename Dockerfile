# Use an official OpenJDK runtime as a parent image
FROM maven

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=production

# Create a directory for your Spring Boot application
WORKDIR /app

RUN mkdir /app/tmp

# Copy the Spring Boot JAR file from the host into the container
COPY target/VideoProcessing-0.9.jar /app/

# Download ffmpeg statically compiled binary (adjust URL as needed)
RUN apt update
RUN apt install -y ffmpeg

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "VideoProcessing-0.9.jar"]
