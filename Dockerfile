FROM openjdk:8
COPY target/PictureBackend-*.jar /backend.jar
USER root
RUN mkdir -p /data
WORKDIR /data
CMD ["java", "-jar", "/backend.jar"]