FROM openjdk:8
COPY target/PictureBackend-*.jar /backend.jar
RUN mkdir -p /data
RUN chown newuser /data
USER newuser
WORKDIR /data
CMD ["java", "-jar", "/backend.jar"]