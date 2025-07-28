FROM openjdk:17-alpine
WORKDIR /app
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar
CMD [ "java", "-jar", "app.jar"]