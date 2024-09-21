# Docker Basic Assignment

### Create a Simple Web Application

A retail-store-discount web application for demonstration. Here is a docker-basic-assignment in Java example:

#Database

In this module, we will show how to create a custom database-backed UserDetailsService for authentication with Spring Security.

### How to configured MongoDB?
	
	Follow video : https://www.youtube.com/watch?v=HjDyv7gL4Wg&list=PLA3GkZPtsafacdBLdd3p1DyRd5FGfr3Ue&index=18
	
	1. Create account here : 
	
	https://cloud.mongodb.com/v2/6687c38f8ea3d1541d8a0f49#/metrics/replicaSet/6687c45c9885a8102057d94a/explorer/journaldb/users/find
	
	2. If you face, your application is not started due to mongo db IP address then you need to registered you current IP address to MongoDB atlas
	 	
	   MongoDB Atlas -> Network Access -> Add current IP address if not added under 'IP Access List'
	   
	3. also add current IP address and enable cluster in below path
	 	
	   MongoDB Atlas -> Database -> Add current IP address if not added under 'IP Access List'

## Installing

After checking out the git repo run the following maven command

```bash
mvn clean install
```

This should install all packages, run all unit tests

## Starting

To start the server please run the following maven command

```bash
mvn spring-boot:run
```


### *Java*
  - Java 17


### Write a `Dockerfile` to Containerize the Application

```
# Specifies the parent image for which we’re building the current image. Here we’re using JDK 17 to do that.
FROM openjdk:17

USER root
# Set the working directory
WORKDIR /apps

# The application's jar file
ARG JAR_FILE=target/docker-basic-assignment-*.jar

# Copy directories or files from the source directory and add them to the file system of the image at the path. 
# Here we need to copy our application .jar file from the target folder.
ADD ${JAR_FILE} /apps/docker-basic-assignment.jar


# This command informs docker that the container listens to this port at the run time. Our spring boot application port is 8080.
EXPOSE 8080

# This command allows us to specify the command which docker uses to run our application. Here we need to run the generated jar file. 
# So we need to specify the command java -jar /apps/docker-basic-assignment.jar to run our application.
ENTRYPOINT ["java","-jar","/apps/docker-basic-assignment.jar"]



```


### Build the Docker Image and Run a Container from It
**Build the Docker Image**
 
```
   docker build -t docker-basic-assignment:0.0.1-SNAPSHOT .
```

**Run the Docker Container**

   ```
   docker run -p 8080:8080 docker-basic-assignment:0.0.1-SNAPSHOT
   ```
   
**Run the Docker Container in detached mode**

   ```
   docker run -p 8080:8080 -d docker-basic-assignment:0.0.1-SNAPSHOT
   ```
   
	   
### Use Docker Commands to Manage Containers

**List Running Containers**: 

```
   docker ps
```

**Start a Container**:  

```
   docker start <container_id>
```

**Stop a Container**:  

```
   docker stop <container_id>
```

**Remove a Container**:  

```
   docker rm <container_id>
```
   

### Inspect Running Containers and View Logs

**Inspect a Container**:  
   
```
   docker inspect <container_id>
```

**View Container Logs**:  
  
```
   docker logs <container_id>
```

**View Container tail Logs**:  
  
```
   docker logs -f <container_id>
```


### Write a `docker-compose.yml` File for a Multi-Container Application

**Create a `docker-compose.yml` File**:  
   
   ```yaml
   version: '3'
   services:
     web:
       build: .
       ports:
         - "8080:8080"
     redis:
       image: "redis:alpine"
   ```
   - This example defines a web service that builds from the current directory and a Redis service using the Redis image.
   
   
### Use Docker Compose to Manage the Application

**Bring Up the Application**:  
  
   ```
   docker compose up
   ```
   - This command builds, (re)creates, starts, and attaches to containers for a service.

**Ensure All Services Are Running**:  
  
   ```
   docker-compose ps
   ```
   

### Create a Private Docker Registry or Use Docker Hub
 
**Sign in to Docker Hub**:  

  ```
   docker login
  ```
   - Enter your Docker Hub credentials when prompted.
   
   
### Push Your Docker Images to the Registry

**Tag the Image**:  

  ```
   docker tag docker-basic-assignment:0.0.1-SNAPSHOT your_dockerhub_username/docker-basic-assignment:0.0.1-SNAPSHOT
  ```

**Push the Image**: 
 
  ```
   docker push your_dockerhub_username/docker-basic-assignment:0.0.1-SNAPSHOT
  ```

  
### Pull the Images from the Registry and Run Them Locally

**Pull the Image**:  
  
   ```
   docker pull your_dockerhub_username/docker-basic-assignment:0.0.1-SNAPSHOT
   ```

**Run the Pulled Image**:  
  
   ```
   docker run -p 5000:5001 your_dockerhub_username/docker-basic-assignment:0.0.1-SNAPSHOT
   ```