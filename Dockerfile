FROM eclipse-temurin:21
RUN mkdir /opt/propiedad
COPY ./target/propiedad-service-0.0.1-SNAPSHOT.jar /opt/propiedad
CMD ["java","-jar", "/opt/propiedad/propiedad-service-0.0.1-SNAPSHOT.jar"]