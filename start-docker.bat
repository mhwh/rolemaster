@echo off
REM Assuming docker network nrolemaster have been created with a bridge driver

REM First start the postgres database
start start-docker-postgres.bat

REM Wait for postgres to start up, 5 sec. is plenty of time for the container to start...
ping -n 5 127.0.0.1 > nul

REM #### Assuming the project is updated, and have been build 'package'
REM # mvn package
REM # docker build -f src/main/docker/Dockerfile.jvm -t quarkus/rolemaster-jvm .

docker run -i --rm --network="nrolemaster" --name rolemaster -p 9000:9000 quarkus/rolemaster-jvm