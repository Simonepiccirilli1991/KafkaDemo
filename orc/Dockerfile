FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/OrderOrchestrator-0.0.1-SNAPSHOT.jar /app/OrderOrchestrator.jar

# Expose the port
EXPOSE 8088
  
LABEL name="orderorchestrator-img"

# Run the application
CMD ["java", "-jar", "OrderOrchestrator.jar"]
