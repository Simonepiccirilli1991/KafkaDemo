FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/CacheHandler-0.0.1-SNAPSHOT.jar /app/CacheHandler.jar

# Expose the port
EXPOSE 8084
  
LABEL name="cachehandler-img"

# Run the application
CMD ["java", "-jar", "CacheHandler.jar"]
