@echo off

REM starts the postgres client PGADMIN.
REM username is the email...

REM Add a new server:
REM host: rolemaster_db
REM port: 5432
REM database: rolemaster_db

docker run -it --rm=true --network="nrolemaster" -p 8000:80 -e "PGADMIN_DEFAULT_EMAIL=admin@dummy.com" -e "PGADMIN_DEFAULT_PASSWORD=badeand" -d dpage/pgadmin4
