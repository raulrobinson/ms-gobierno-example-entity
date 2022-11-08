# ------------------------------------------------------------------------
#
# Directorio de ubicacion de todos los microservicio /opt/<NOMBRE>
#
# docker build -f Dockerfile -t telco-micro-java-arch-restblank .
# 
# ------------------------------------------------------------------------

FROM openjdk:11-jre-slim-buster
COPY ./src/telco-micro-java-arch-restblank.jar /opt/microservice/telco-micro-java-arch-restblank.jar
ENTRYPOINT ["java","-jar","/opt/microservice/telco-micro-java-arch-restblank.jar"]