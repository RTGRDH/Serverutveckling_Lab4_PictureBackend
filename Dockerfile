FROM openjdk:8
COPY target/PictureBackend-*.jar /backend.jar
CMD ["java", "-jar", "/backend.jar"]