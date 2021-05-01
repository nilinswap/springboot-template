# syntax=docker/dockerfile:experimental
FROM maven:3.6.3-jdk-11-slim AS dependency
WORKDIR /usr/app
COPY ./pom.xml .
RUN chmod +x pom.xml
RUN mvn dependency:go-offline -B --fail-never

# Build JAR
FROM dependency as build
WORKDIR /usr/app
COPY ./src src
COPY ./lombok.config .
RUN --mount=type=cache,target=.m2 mvn clean install -DskipTests=true


FROM openjdk:11-jre-slim
WORKDIR /usr/app

# need to uncomment this
#COPY newrelic.jar .
#COPY newrelic.yml .


EXPOSE 8008
WORKDIR /usr/app
# need to remove below line.
COPY secrets.json .
COPY --from=build /usr/app/target/sbtemplate-*.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=local", "app.jar"]

