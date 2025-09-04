# Base image with JDK 17 (Temurin is the recommended replacement for OpenJDK images)
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper & config first for dependency caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable & download dependencies (cached if pom.xml doesn't change)
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose Render's dynamic port
EXPOSE 8080

# Run the app, binding to $PORT
CMD ["java", "-jar", "-Dserver.port=${PORT:-8080}", "target/finance-0.0.1-SNAPSHOT.jar"]