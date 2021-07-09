FROM openjdk:8-jdk-alpine
EXPOSE 8082
#VOLUME /var/lib/mysql/data
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} note.jar
ENTRYPOINT ["java","-jar","/note.jar"]