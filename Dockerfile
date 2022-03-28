FROM openjdk:8-jdk-alpine
COPY build/libs/dollar-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]