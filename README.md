# blusalt-drone-service

# Requirement:
1. Java 11
2. Postgresql

# How to run the project:
1. create a folder
2. Clone the project to the new folder 
3. Setting up postgres with docker if you don't have postgres install locally on your system
   1. Install and Configure PSQL using Docker:
      `docker run --name postgresql -p 5432:5432 -e POSTGRES_PASSWORD=blusalt123@ -e POSTGRES_USER=blusalt -e POSTGRES_DB=drone_db -d postgres` this create image and start the container for postgresql
   2. Using local postgres: create new user and new db to new user
4. Building the project: to build the project env values must be set, if you're building from intellij you can set the values on the IDE
     Set environment variables for these values (DB_HOST, DB_NAME, DB_USER and  DB_PASSWORD) matching the information of the database you created above.
   run `.\mvnw clean` to build the project
5. running the project
   on the project root dir run `.\mvnw spring-boot:run`
6. access project swagger UI on http://localhost:8060/swagger-ui/
7. API can also be test through postman


