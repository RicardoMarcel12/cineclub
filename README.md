# cineclub
test for applaudo studios

## version: 0.2 
Security Implemented

# Install
implemented with MySQL Database 8.0.17 (Community Version GPL) 
create a schema and a user and register the credentials to file `\src\main\resources\application.properties`
,jpa will generate the model

## application.properties example:
|spring.datasource.url = jdbc:mysql://127.0.0.1:3306/cineclub?serverTimezone=UTC&useSSL=false|
|spring.datasource.username = cineclub|
|spring.datasource.password = zWJt2GEYxt7C|

# Run 
for running use the command `mvn spring-boot:run` 

# Movie Rental Security
API security implemented with JWT with an expiration time of 1 hour

## Login
first you need to login to
  
|URL:	| /auth/login|
|METHOD:	| POST|
|HEADER:	| Content-Type:application/json|
|BODY:	| {"username":"admin", "password":"12345"}|

use the Generated token with Authentication type "Bearer Token"

# movie management:

## GET MOVIE LIST
|URL:	|/m1/movies|
|METHOD:	|GET|
	
## INSERT MOVIE
|URL:	|/m1/movies|
|METHOD:	| POST|
|HEADER:	| Content-Type:application/json|
|BODY:	|{"movieId": 0,"title": "Movie Title","description": "brief Description","imageUrl": "https://server/image.ext","stock": 1,"rentalPrice": 0.99,"salePrice": 1.99,"isAvailable": true	}|
		
		
## VIEW MOVIE
|URL:	| /m1/movies/{id}|
|METHOD:	| GET|
	
## UPDATE MOVIE
|URL:	| /m1/movies/{id}|
|METHOD:	| PUT|
|HEADER:	| Content-Type:application/json|
|BODY:	|{"movieId": 0,"title": "Movie Title","description": "brief Description","imageUrl": "https://server/image.ext","stock": 1,"rentalPrice": 0.99,"salePrice": 1.99,"isAvailable": true	}|

## DELETE MOVIE
|URL:	| /m1/movies/{id}|
|METHOD:	| DELETE|	