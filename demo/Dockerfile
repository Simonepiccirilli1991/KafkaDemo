FROM openjdk:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/order-composition-0.0.1-SNAPSHOT.jar /app/order-composition.jar

# Expose the port
EXPOSE 8082
  
LABEL name="ordercomposition-img"

# Run the application
CMD ["java", "-jar", "order-composition.jar"]
