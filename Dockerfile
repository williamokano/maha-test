FROM gradle:7.0.0-jdk11 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN /usr/src/app/gradlew clean build --no-daemon

FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY --from=build /usr/src/app/build/libs/maha-test-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]