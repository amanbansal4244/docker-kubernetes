#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.5-openjdk-17 as stage1

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

USER root

# set working directory
WORKDIR /app

# copy your other files
COPY . .

# compile the source code and package it in a jar file
#RUN mvn clean install -Dmaven.test.skip=true

#Stage 2
# set base image for second stage
FROM openjdk:17-alpine

# set deployment directory
WORKDIR /app

# copy over the built artifact from the maven image
COPY --from=stage1 /app/target/docker-product-server-*.jar /app

# This command informs docker that the container listens to this port at the run time. Our spring boot application port is 8080.
EXPOSE 9999

# This command allows us to specify the command which docker uses to run our application. Here we need to run the generated jar file. 
# So we need to specify the command java -jar /apps/docker-basic-assignment.jar to run our application.
ENTRYPOINT ["java","-jar","docker-product-server.jar"]
