# Stage 1: development-stage
# Using a base image with JDK 17 and Maven for first stage and 'build-stage' is just the name of the stage till we start stage 2
# here, we are using complete version of java means JDK to build and create the jar, if we dont take full image then , we might face
# issue while installing some software or building the jar.
FROM maven:3.8.5-openjdk-17 as development-stage

USER root

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

ENV APP_HOME=/usr/app/
# set working directory
WORKDIR $APP_HOME

COPY pom.xml .

# go-offline using the pom.xml
RUN mvn dependency:go-offline

# copy your other files
COPY . .
RUN mvn clean test

# Stage 2: builder-stage
FROM maven:3.8.5-openjdk-17 as builder-stage

ENV APP_HOME=/usr/app/
# set working directory
WORKDIR $APP_HOME

COPY pom.xml .
COPY . .
# compile the source code and package it in a jar file
RUN mvn clean package -DskipTests


# Stage 3: production-stage
# set base image for second stage
# here we are using smaller version of JDK because to run application, we need JRE, not JDK. So size of image will be reduced.
# alpine is small image, not heavy image. Other ways of image is like -alpine, -jre-slim etc.
FROM openjdk:17-alpine as production-stage

ENV ARTIFACT_NAME=springboot-mongodb-k8s.jar
ENV APP_HOME=/usr/app/
# we have to set deployment directory again, so we can use same directory.
WORKDIR $APP_HOME


# COPY --from=<previous-stage-name> <source> <destination>
# COPY the above stage1 as compressed, from path /app/target/*.jar to new stage path : $APP_HOME
COPY --from=builder-stage $APP_HOME/target/springboot-mongodb-k8s-*.jar $APP_HOME/$ARTIFACT_NAME


# This command informs docker that the container listens to this port at the run time. 
# Make port 9999 available to the world outside this container
EXPOSE 9999

# This command allows us to specify the command which docker uses to run our application. Here we need to run the generated jar file. 
# So we need to specify the command java -jar /apps/docker-basic-assignment.jar to run our application.
ENTRYPOINT ["java","-jar","springboot-mongodb-k8s.jar"]