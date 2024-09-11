# Docker Advanced Assignment
This document provides an overview of the tasks performed during the Docker assignment. The assignment focused on optimizing Dockerfiles, managing Docker networks and volumes, and implementing security best practices.


## 1. Write a `Dockerfile` to Containerize the Application

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


## 2. Optimize a `Dockerfile` for Build Speed and Image Size

### **Original Dockerfile**

The Dockerfile was optimized by using multi-stage builds and reducing the final image size. Here is the optimized Dockerfile:

   
   
## 3. Setup a Custom Docker Network

### **Docker Compose File: `docker-compose.yml`**
This file defines two containers connected to a custom Docker network.

```yaml
version: '3.8'

services:
    message-server:
        container_name: message-server
        build:
            context: docker-message-server
            dockerfile: Dockerfile
        image: message-server:latest 
        ports:
            - 18888:8888
        networks: 
            - custom-spring-cloud-network
        restart: always
    product-server:
        container_name: product-server
        build:
            context: docker-product-server
            dockerfile: Dockerfile
        image: product-server:latest
        ports:
            - 19999:9999
        networks:
            - spring-cloud-network
        restart: on-failure #unless-stopped
        
networks: 
    custom-spring-cloud-network:
        driver: bridge
```


### **Steps to Use Docker Compose**

**Create the Docker Compose File**

```bash
   touch docker-compose.yml
 ```
 Paste the YAML content into this file.
 
 **To check build-file for syntax-errors**:

    ```bash
    docker-compose config
    ```

**Start the Docker Daemon**:

    ```bash
    sudo dockerd
    ```
 **Build the Docker Image**:
 
    ```bash
    docker buildx build -f Dockerfile_multistage -t docker_multistage .
    ```
**Run Docker Compose**:

    ```bash
    docker-compose up -d
    ```

**Verify Running Containers**:

    ```bash
    docker-compose ps
    ```

**Inspect the Network**:

    ```bash
    docker network inspect my_custom_network
    ```

## 3. Create and Manage Docker Volumes

### **Creating Docker Volumes**:
```bash
docker volume create my_volume
```

### **Using Named Volumes in Containers**:
```bash
docker run -d --name my_container -v my_volume:/data docker_multistage
```

### **List Docker Volumes**:
```bash
docker volume ls
```

### **Inspect Docker Volumes**:
```bash
docker volume inspect my_volume
```

### **Remove Docker Volumes**:
```bash
docker volume rm my_volume
```

### **Using Bind Mounts**:
```bash
docker run -d --name my_container -v /host/path:/container/path docker_multistage
```

### **Example with Multiple Containers**:
```bash
docker run -d --name app1 -v /host/data:/shared_data docker_multistage
docker run -d --name app2 -v /host/data:/shared_data docker_multistage
```

### **Backup and Restore Data from Docker Volumes**

#### **Backing Up Data**:
```bash
docker run --rm -v my_volume:/data -v $(pwd):/backup alpine tar czf /backup/backup.tar.gz -C /data .
```

#### **Restoring Data**:
```bash
docker run --rm -v my_volume:/data -v $(pwd):/backup alpine sh -c "tar xzf /backup/backup.tar.gz -C /data"
```

## 4. Implement Security Best Practices

### **User Permissions**:
```Dockerfile
# Add this to your Dockerfile
RUN adduser -D myuser
USER myuser
```

### **Image Vulnerability Scanning**:
```bash
docker scan docker_multistage
```

### **Secret Management**:
```bash
echo "my_secret" | docker secret create my_secret -
docker service create --name my_service --secret my_secret docker_multistage
```

## 5. Run Containers with Least Privilege

### **Run Containers with a Non-Root User**:
```bash
docker run --user 1000:1000 docker_multistage
```

### **Drop Unnecessary Capabilities**:
```bash
docker run --cap-drop=ALL docker_multistage
```

## 6. Use Docker Bench for Security

### **Install Docker Bench for Security**:
```bash
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security
```

### **Run Docker Bench for Security**:
```bash
sudo ./docker-bench-security.sh
```
