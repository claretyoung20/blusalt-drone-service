# Build Status
[![Build Status](http://139.59.179.8:8080/buildStatus/icon?job=buildStatus)](http://139.59.179.8:8080/job/buildStatus/)
# blusalt-drone-service

# Requirement:
1. Java 11
2. Postgresql

# How to run the project:
1. create a new folder
2. Clone the project to the new folder 
3. Set up postgres with docker if you don't have postgres install locally on your system
   1. Install and Configure PSQL using Docker:
      `docker run --name postgresql -p 5432:5432 -e POSTGRES_PASSWORD=blusalt123@ -e POSTGRES_USER=blusalt -e POSTGRES_DB=drone_db -d postgres` this create image and start the container for postgresql
   2. Using local postgres: create new user and new db to new user
4. Building the project: to build the project env values must be set, if you're building from intellij you can set the values on the IDE
     Set environment variables for these values (DB_HOST, DB_NAME, DB_USER and  DB_PASSWORD) matching the information of the database you created above.
   run `.\mvnw clean` to build the project
5. running the project
   on the project root dir run `.\mvnw spring-boot:run`
6. access project swagger UI on http://localhost:8060/swagger-ui/
7. API can also be tested through postman

# Running project through Kubernetes
1. cd to deployment folder on the project root dir
2. run `kubectl apply -f namespace.yaml,secrets.yaml,configmap.yaml`
3. run `kubectl apply -f pg` to create deployment and service for postgresql
4. run `kubectl apply -f drone` to create drone service deployment and service
5. Using port-forward to have access to our service run `kubectl port-forward svc/droneservice 8060:8060 -n blusalt`
6. access project swagger UI on http://localhost:8060/swagger-ui/
7. API can also be tested through postman

having any issue contact: Young Nnenna M.C via claretyoung@gmail.com

# Running project through helm
#### cd to charts folder on the project root dir
First of all modify values.yaml file under config and secret provide data base details.
 1. `cd charts`
 2. `helm dependency update drone-service/ `
 3. `helm install blusalt drone-service/ -n default` you can use any namespace
 4. the service is available at http://drone.service/ but first you need to update you host file with ip address, you can check online how to change your OS host file and
 5. for mac M1 run `sudo vi /etc/host` add the loadbalancer external IP of traefik service to get traefik ip `kubectl get svc -n default` and the dns name like this  "167.00.00.00 drone.service" change the ip to you LB ip and flush DNS
    `sudo dscacheutil -flushcache; sudo killall -HUP mDNSResponder`
visit http://drone.service/swagger-ui/#/

