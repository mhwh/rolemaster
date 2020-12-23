### Create the docker network, for all containers to be added to.
docker network create -d bridge nrolemaster

#### Start the database, with the connection to the newly created network
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --network="nrolemaster" --name rolemaster_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rolemaster_db -p 5432:5432 postgres

#### Build the docker image of this application
#### And start it on the same network as above...
docker run -i --rm --network="nrolemaster" --name rolemaster -p 9000:9000 quarkus/rolemaster

