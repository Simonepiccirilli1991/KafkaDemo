FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/Otpv-0.0.1-SNAPSHOT.jar /app/Otpv.jar

# Expose the port
EXPOSE 8083
  
LABEL name="otpv-img"

# Run the application
CMD ["java", "-jar", "Otpv.jar"]
