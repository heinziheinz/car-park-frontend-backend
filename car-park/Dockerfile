
FROM eclipse-temurin:17-jdk-alpine as bulidstage

RUN apk add maven


COPY pom.xml .
COPY src src
RUN  mvn package

FROM eclipse-temurin:17-jdk-alpine
COPY --from=bulidstage target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]