# rolemaster
A microservice implementation of the data/functionality you would need in order for you to create a new character in Rolemaster.

Change history:
No released version yet...

Swagger URL: http://localhost:9000/swagger-ui
Metrics: http://localhost:9000/metrics/application (accept json for at better read...)

Goals:
Version 1.0: 
A implementation of the weapons, attacktables and critical tables defined in Armslaw. Nothing more or less, only the weapons and their attacktable, and crittables.
This version also contains a 'hit' method, in order for you to test the table.
Check out the swaggerdocumentation for more description of the service and its methods.

Start the quarkus server in dev mode (mvn quarkus:dev) and go to http://localhost:8080/swagger-ui to have a testlook...


