@echo off

REM starts the postgres database container.
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --network="nrolemaster" --name rolemaster_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rolemaster_db -p 5432:5432 postgres
