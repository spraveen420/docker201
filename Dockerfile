FROM openjdk:latest

COPY docker201-0.0.1-SNAPSHOT.jar /usr/src/docker201-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java","-jar", "/usr/src/docker201-0.0.1-SNAPSHOT.jar"]