# Specifies the parent image for which we’re building the current image. Here we’re using JDK 17 to do that.
#FROM openjdk:17

# install the image of the Maven – JDK version.
FROM maven:3.8.5-openjdk-17

USER root

# Set the path of working directory
WORKDIR /app

# copy all the files inside the project directory to the container.
COPY . .

# execute a command-line inside the container: mvn clean install to install the dependencies in pom.xml.
#RUN mvn clean install

# run script mvn spring-boot:run after the image is built.
#CMD mvn spring-boot:run

# The application's jar file
ARG JAR_FILE=target/docker-message-server-*.jar

# Copy the JAR file from the build context into the Docker image
# this will get copied under WORKDIR path which is /app
COPY ${JAR_FILE} docker-message-server.jar


# This command informs docker that the container listens to this port at the run time. Our spring boot application port is 8080.
#EXPOSE 8080

# This command allows us to specify the command which docker uses to run our application. Here we need to run the generated jar file. 
# So we need to specify the command java -jar /apps/docker-basic-assignment.jar to run our application.
ENTRYPOINT ["java","-jar","docker-message-server.jar"]
