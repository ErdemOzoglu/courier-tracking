FROM openjdk:17-alpine

COPY target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]

EXPOSE 8080
