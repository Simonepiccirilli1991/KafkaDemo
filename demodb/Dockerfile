FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/demodb-0.0.1-SNAPSHOT.jar /app/demodb.jar

# Expose the port
EXPOSE 8081
  
LABEL name="demodb-img"

# Run the application
CMD ["java", "-jar", "demodb.jar"]
