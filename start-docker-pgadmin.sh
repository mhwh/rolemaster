# starts the postgres client PGADMIN.
# username is the email...

# Add a new server:
# host: rolemaster_db
# port: 5432
# database: rolemaster_db

sudo docker run -it --rm=true --network="nrolemaster" -p 10000:80 -e "PGADMIN_DEFAULT_EMAIL=admin@dummy.com" -e "PGADMIN_DEFAULT_PASSWORD=badeand" -d dpage/pgadmin4
