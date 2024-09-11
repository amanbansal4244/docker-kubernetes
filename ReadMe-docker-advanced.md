# Docker Advanced Assignment
The assignment focused on optimizing Docker files, managing Docker networks and volumes, and implementing security best practices.


## 2. Optimize a `Dockerfile` for Build Speed and Image Size

### **Original Dockerfile**

The Dockerfile was optimized by using multi-stage builds and reducing the final image size. Here is the optimized Dockerfile:

```
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
COPY --from=stage1 /app/target/docker-message-server-*.jar /app

# This command informs docker that the container listens to this port at the run time. Our spring boot application port is 8080.
EXPOSE 7777

# This command allows us to specify the command which docker uses to run our application. Here we need to run the generated jar file. 
# So we need to specify the command java -jar /apps/docker-basic-assignment.jar to run our application.
ENTRYPOINT ["java","-jar","docker-message-server.jar"]

```
   
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
            - 7777:7777
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
            - 9999:9999
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
    docker compose config
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
