#!/bin/bash

# Build script for Render deployment
echo "Starting build process..."

# Make mvnw executable
chmod +x ./mvnw

# Clean and build the application
echo "Building Spring Boot application..."
./mvnw clean package -DskipTests

echo "Build completed successfully!"