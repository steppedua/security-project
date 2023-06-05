FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
ARG JAR_FILE=build/libs/security-project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} security-project.jar
ENTRYPOINT ["java","-jar","security-project.jar"]