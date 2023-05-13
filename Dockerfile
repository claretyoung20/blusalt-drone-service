FROM  openjdk:11.0.11-9-jdk-slim
VOLUME /tmp
EXPOSE 8060
COPY target/DroneService.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JxAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
