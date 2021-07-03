FROM openjdk:8-jdk-alpine
EXPOSE 8082
VOLUME /var/lib/mysql/data
ARG JAR_FILE=target/P9_Note-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} p9_note.jar
ENTRYPOINT ["java","-jar","/p9_note.jar"]