# Image Optimization Service

## Description

This project has been bootstrapped with [Spring Boot](https://github.com/spring-projects/spring-boot) framework. 
It is responsible for serving and optimizing images based on predefined properties.


## Configuration

Make sure to update application.properties with the correct values
```bash
app.source.root.url
app.aws.accesskey
app.aws.secretkey
```

## Running the app

Project will be running at http://localhost:8080.

```bash
# package project as jar
$ mvn clean package

# execute the jar
$ java -jar image-service:1.0.0
```

using docker

```bash
# build the container
$ docker build -t example-image-service:1.0.0 .

# run the container
$ docker run -d -p 8080:8080 example-image-service:1.0.0
```

## TODO List

1. Modify bufferedImage for optimization
2. Delete all images (Need to change enum implementation)
3. Add more unit tests


## Support

Please reach out to [Harith](https://github.com/harixth) if you faced any issue with this project.