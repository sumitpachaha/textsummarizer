# Use lightweight Java 8 image
FROM eclipse-temurin:8-jdk

WORKDIR /app

# Copy the JAR file into the container
COPY target/AutoTextSummarizer-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/static/config/config.json /app/config/config.json

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]
