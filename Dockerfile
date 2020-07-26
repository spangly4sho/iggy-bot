FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
WORKDIR /usr/local/run
COPY content/ /usr/local/run/content/
COPY ${JAR_FILE} /usr/local/run/app.jar
ENTRYPOINT ["java","-jar","/usr/local/run/app.jar"]