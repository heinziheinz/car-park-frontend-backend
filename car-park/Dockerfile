#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
FROM eclipse-temurin:17-jdk-alpine as bulidstage
WORKDIR /target
RUN apk add maven

#COPY mvnw .
#COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN  mvn package
COPY target/*.jar app.jar

FROM eclipse-temurin:17-jdk-alpine
COPY --from=bulidstage /target/app.jar .
ENTRYPOINT ["java","-jar","/app.jar"]