# Use OpenJDK 17 as base image (slim version for smaller size)
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port (Render will use the PORT environment variable)
EXPOSE ${PORT:-8080}

# Run the application
CMD ["java", "-jar", "-Dserver.port=${PORT:-8080}", "target/finance-0.0.1-SNAPSHOT.jar"]