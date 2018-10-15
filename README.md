dir-list-api
======

Introduction
----
This is a basic Java Spring application that exposes a single API to get a directory listing of a specified directory. 

Building JAR file
----
Build a runnable JAR file with the following Maven command.
```
mvn clean install
```
This will build and export a JAR file in the `docker` folder.

Building Docker container
----
The `docker` folder contains a `Dockerfile` file to build a new Docker image. 
The following commands can be used to build and run the a new Docker container.
```
docker build -t dir-list-api:latest .
docker run -d -p 8080:8080 --name Wikus dir-list-api:latest
```

Testing the API
----
After starting up the application you should be able to see an output for:
```
http://localhost:8080/dir
```

API information
----
This program only exposes a single API `/dir`. This API uses a `@RequestParam` called `path` to determine what directory to return the listing of. If no `@RequestParam` is sent, it will return a listing of the root folder. 
```
http://localhost:8080/dir?path=/
```