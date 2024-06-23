FROM openjdk:17-jdk-slim

WORKDIR /app

COPY out/artifacts/database_jar/database.jar /app/database.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/database.jar"]