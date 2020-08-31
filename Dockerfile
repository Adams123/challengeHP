FROM openjdk:11-jdk
EXPOSE 3000
ARG JAR_FILE=target/hp-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]