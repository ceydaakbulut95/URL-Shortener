FROM openjdk:19-jdk AS builder
LABEL authors="ceydaakbulut"

WORKDIR /app

COPY target/url_shortener_p1-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
