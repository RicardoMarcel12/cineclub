# cineclub
test for applaudo studios

version: 0.2 

Security Implemented

the database used is MySQL just create a schema and a user with the name 'cineclub' and register the credentials to 
the file in \src\main\resources\application.properties and then with a mvn build the structure will be created by jpa into the schema

required mysql setup on application.properties example:
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/cineclub?serverTimezone=UTC&useSSL=false
spring.datasource.username = cineclub
spring.datasource.password = zWJt2GEYxt7C

first do a mvn build 
for running use the command mvn spring-boot:run and take note of the password generated 

first you need to login to 
	URL: localhost:8080/auth/login
	METHOD: POST
	HEADER: Content-Type:application/json
	BODY: {"username":"admin", "password":"12345"}

and then take the Generated token and save it to make operations to Movies

Available options for movie management:

  GET MOVIE LIST
	URL:localhost:8080/m1/movies
	METHOD:GET
	
  INSERT MOVIE
  	URL: localhost:8080/m1/movies/
	METHOD: POST
	HEADER: Content-Type:application/json
	BODY:{
		    "movieId": 0,
		    "title": "Movie Title",
		    "description": "brief Description",
		    "imageUrl": "https://server/image.ext",
		    "stock": 1, //integer
		    "rentalPrice": 0.99, //BigDecimal
		    "salePrice": 1.99,// BigDecimal
		    "isAvailable": true //Boolean
		}
		
   VIEW MOVIE
   	 URL: localhost:8080/m1/movies/{id}
	 METHOD: GET
	
   UPDATE MOVIE
   	 URL: localhost:8080/m1/movies/{id}
	 METHOD: PUT
	 HEADER: Content-Type:application/json
	 BODY:{
		    "title": "Movie Title",
		    "description": "brief Description",
		    "imageUrl": "https://server/image.ext",
		    "stock": 1, //integer
		    "rentalPrice": 0.99, //BigDecimal
		    "salePrice": 1.99,// BigDecimal
		    "isAvailable": true //Boolean
		  } 

   DELETE MOVIE
	 URL: localhost:8080/m1/movies/{id}
	 METHOD: DELETE
	 	
		
		