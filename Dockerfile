# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy everything into the container
COPY . /app

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the app and skip tests
RUN ./mvnw package -DskipTests

# Run the jar file (update name if needed)
CMD ["java", "-jar", "target/smartcontactmanager-0.0.1-SNAPSHOT.jar"]
