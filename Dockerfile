FROM openjdk:8-jre-alpine

VOLUME /tmp

ADD target/*.jar app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]