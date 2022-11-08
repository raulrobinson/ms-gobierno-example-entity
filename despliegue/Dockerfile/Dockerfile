# ------------------------------------------------------------------------
#
# Directorio de ubicacion de todos los microservicio /opt/<NOMBRE>
#
# docker build -f Dockerfile -t telco-micro-java-arch-restblank .
# 
# ------------------------------------------------------------------------

FROM openjdk:11-jre-slim-buster
RUN apt-get update && apt-get install curl -y
COPY ./src/ms-gobierno-example-entity.jar /opt/microservice/ms-gobierno-example-entity.jar
ENTRYPOINT ["java","-Duser.timezone=CO","-jar","/opt/microservice/ms-gobierno-example-entity.jar"]