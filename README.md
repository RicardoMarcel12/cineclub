# cineclub
test for applaudo studios

## version: 0.2 
Security Implemented

## Install
implemented with MySQL Database 8.0.17 (Community Version GPL) 
create a schema and a user and register the credentials to file `\src\main\resources\application.properties`
,jpa will generate the model

### application.properties example:
```
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/cineclub?serverTimezone=UTC&useSSL=false
spring.datasource.username = cineclub
spring.datasource.password = zWJt2GEYxt7C

```
## Run 
for running use the command `mvn spring-boot:run` 

## Movie Rental Security
API security implemented with JWT with an expiration time of 1 hour

### Login
first you need to login to

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|  
| URL:      | /auth/login                             |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | {"username":"admin", "password":"12345"}|

use the Generated token with Authentication type **"Bearer Token"**

## movie management:

### GET MOVIE LIST
	Get a list of available movies, no authentication required

| PROP      | VALUE        |
|-----------	|--------------|
| URL:      | /m1/movies   |
| METHOD:   | GET          |

#### FILTERS, SORT AND PAGE PARAMS
all parameters are optional

| PARAMETER | VALUE EXAMPLE   |           DESCRIPTION			                  |
|-----------	|-----------------|---------------------------------------------------|
| title=    | 'matrix'	      | matches any part of the movie title               | 
| page=  	| 0               | page number starting with zero                    |
| size=		| 5               | number of elements on page         	              |
| sort=     | movieId, -title | any field of the movie, dash for descending order |	

all posible sort fields are:
- title
- description
- stock
- rentalPrice
- salePrice	
	
### GET ADMINISTRATOR MOVIE LIST	
Get a list of all movies, admin role required

| PROP      | VALUE              |
|-----------	|--------------------|
| URL:      | /m1/movies/admin   |
| METHOD:   | GET                |

#### FILTERS, SORT AND PAGE PARAMS
all parameters are optional

| PARAMETER  | VALUE EXAMPLE   |           DESCRIPTION			                  |
|------------|-----------------|---------------------------------------------------|
| title=     | 'matrix'	       | matches any part of the movie title               | 
| isAvailable| true, false     | filters available/unavailable movies              |
| page=  	 | 0               | page number starting with zero                    |
| size=		 | 5               | number of elements on page         	               |
| sort=      | movieId, -title | any field of the movie, dash for descending order |	

all posible sort fields are:
- title
- description
- stock
- rentalPrice
- salePrice
			
	
### INSERT MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/movies                              |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | `{"movieId": 0,"title": "Movie Title","description": "brief Description","imageUrl": "https://server/image.ext","stock": 1,"rentalPrice": 0.99,"salePrice": 1.99,"isAvailable": true	}` |
		

### VIEW MOVIE

| PROP      |    VALUE          |
|-----------	|-------------------|
| URL:      | /m1/movies/{id}   |
| METHOD:	| GET               |
	
### UPDATE MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/movies/{id}                         |
| METHOD:	| PUT                                     |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | `{"movieId": 0,"title": "Movie Title","description": "brief Description","imageUrl": "https://server/image.ext","stock": 1,"rentalPrice": 0.99,"salePrice": 1.99,"isAvailable": true	}` |

### DELETE MOVIE

| PROP      |     VALUE        |
|-----------	|------------------|
|URL:       | /m1/movies/{id}  |
|METHOD:	    | DELETE           |	