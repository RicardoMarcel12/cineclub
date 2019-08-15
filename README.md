# cineclub
test for applaudo studios

version: 0.1

the database used is MySQL just create a schema and a user with the name 'cineclub' and register the credentials to 
the file in \src\main\resources\application.properties and then with a mvn build the structure will be created by jpa into the schema

required mysql setup on application.properties example:
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/cineclub?serverTimezone=UTC&useSSL=false
spring.datasource.username = cineclub
spring.datasource.password = zWJt2GEYxt7C


first do a mvn build 
for running use the command mvn spring-boot:run and take note of the password generated 

then you can access the api from localhost:8080/api and login with the username 'user' and the password in the console.
for now theres only one function to get all the users of the App: api/users to get a JSON with the list of users in the Database.
