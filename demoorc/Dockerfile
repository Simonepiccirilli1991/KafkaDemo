FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/order-orc-0.0.1-SNAPSHOT.jar /app/order-orc.jar

# Expose the port
EXPOSE 8085
  
LABEL name="orderorc-img"

# Run the application
CMD ["java", "-jar", "order-orc.jar"]
