# CineClub
test for applaudo studios

## version: 1.3 
- 0.1 Initialization of project
- 0.2 Security Implemented
- 0.3 Filtering, sorting and paging
- 0.4 Like system
- 0.5 Rent and Buy
- 1.0 First Release on Heroku
- 1.1 Register/forgot-password Implemented
- 1.2 Mail implemented
- 1.3 User Roles Administration
 
## Install
### DATABASE
implemented with MySQL Database 8.0.17 (Community Version GPL) 
create a schema and a user and register the credentials to file ` \src\main\resources\application.properties `

#### application.properties example:
```
spring.datasource.url = {jdbc:mysql:connectURL}
spring.datasource.username = {DB_USERNAME}
spring.datasource.password = {DB_PASSWORD}

```
### MAIL
setup your email provider on the ` \src\main\resources\application.properties ` file

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=**user@gmail.com**
spring.mail.password=**secret-password**
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
## Run 
to run the api locally, use the command `mvn spring-boot:run` on the project folder 

## Movie Rental Security: 
API security implemented with JWT with an expiration time of 1 hour

### LOGIN

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|  
| URL:      | /auth/login                             |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | {"username":"admin", "password":"12345"}|

use the Generated token with Authentication type **"Bearer Token"**

### REGISTER NEW USER 

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|  
| URL:      | /api/register                             |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | {"username":"{username}", "password":"{password}", "firstName":"{firstname}", "lastName":"{lastname}", "email":"{emailaddress}"} |

### FORGOT PASSWORD (STEP 1)

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|  
| URL:      | /api/forgot-password                    |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | {"email":"{emailaddress}, "url":{frontend form handler}} |

- {frontend from handler } => a token will be appended to the URL example URL: ` "http://www.cineclub-kansas.com/recover?token=" ` the token will be valid for 30 minutes

### PASSWORD RESET (STEP 2)

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|  
| URL:      | /api/reset-password                     |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | {"newPassword":"{password}", "confirmPassword":"{password}", "token":"{generatedToken}"} |

- {generatedToken} => from the previous step

### GET USER ROLES 
Admin role is needed for this operation
get a string list of the user's roles

| PROP      | VALUE                   |
|-----------	|-------------------------|
| URL:      | /api/roles/{username}   |
| METHOD:   | GET                     |


### SET NEW USER ROLE
Admin role is needed for this operation

| PROP      |                    VALUE                   |
|-----------	|--------------------------------------------|  
| URL:      | /api/roles/{username}                      |
| METHOD:   | PUT                                        |
| HEADER:   | Content-Type:application/json              |
| BODY:	    | {"role":"{new role}"}  					 |

this method will return the final role list for the user
possible role values: 
- ROLE_ADMIN
- ROLE_USER	


### DELETE USER ROLE
Admin role is needed for this operation

| PROP      |                    VALUE                   |
|-----------	|--------------------------------------------|  
| URL:      | /api/roles/{username}                      |
| METHOD:   | DELETE                                     |
| HEADER:   | Content-Type:application/json              |
| BODY:	    | {"role":"{new role}"}                      |

this method will return the final role list for the user
possible role values: 
- ROLE_ADMIN
- ROLE_USER	


## Movie Management:

### GET MOVIE LIST
get a list of available movies, no authentication required

| PROP      | VALUE        |
|-----------	|--------------|
| URL:      | /m1/movies   |
| METHOD:   | GET          |

#### FILTERS, SORT AND PAGE PARAMS
parameters are optional

| PARAMETER | VALUE EXAMPLE         |           DESCRIPTION			                    |
|-----------	|-----------------------|---------------------------------------------------|
| title=    | 'matrix'	            | matches any part of the movie title               | 
| page=  	| 0                     | page number starting with zero                    |
| size=		| 5                     | number of elements on page                        |
| sort=     | "movieId" or "-title" | any field of the movie, dash for descending order |	

all posible sort fields are:
- title
- description
- stock
- rentalPrice
- salePrice
- likes	
	
### GET ADMINISTRATOR MOVIE LIST	
get a list of all movies, admin role required

| PROP      | VALUE              |
|-----------	|--------------------|
| URL:      | /m1/movies/admin   |
| METHOD:   | GET                |

#### FILTERS, SORT AND PAGE PARAMS
parameters are optional

| PARAMETER  | VALUE EXAMPLE         |           DESCRIPTION		                         |
|------------|-----------------------|---------------------------------------------------|
| title=     | 'matrix'	             | matches any part of the movie title               | 
| isAvailable| true, false           | filters available/unavailable movies              |
| page=  	 | 0                     | page number starting with zero                    |
| size=		 | 5               		 | number of elements on page      	                 |
| sort=      | "movieId" or "-title" | any field of the movie, dash for descending order |	

all posible sort fields are:
- title
- description
- stock
- rentalPrice
- salePrice
- likes
			
	
### INSERT MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/movies                              |
| METHOD:   | POST                                    |
| HEADER:   | Content-Type:application/json           |
| BODY:	    | `{"title": "Movie Title", "description": "brief Description", "imageUrl": "https://server/image.ext", "stock": 1, "rentalPrice": 0.99, "salePrice": 1.99, "isAvailable": true}` |
		

### VIEW MOVIE

| PROP      |    VALUE          |
|-----------	|-------------------|
| URL:      | /m1/movies/{id}   |
| METHOD:	| GET               |
	
- {id}=> movieId
	
### UPDATE MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/movies/{id}                         |
| METHOD:	| PUT                                     |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | `{"title": "Movie Title", "description": "brief Description", "imageUrl": "https://server/image.ext", "stock": 1, "rentalPrice": 0.99, "salePrice": 1.99}` |

- {id}=> movieId

### UPDATE MOVIE AVAILABILITY

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/movies/available/{id}               |
| METHOD:	| PUT                                     |
| PARAMETER:| {"available"={boolean}}                 |


- {id}=> movieId
- {boolean} => true or false


### DELETE MOVIE

| PROP      |     VALUE        |
|-----------	|------------------|
|URL:       | /m1/movies/{id}  |
|METHOD:	    | DELETE           |

### GET LIKES BY MOVIE
	
| PROP      |    VALUE          |
|-----------	|-------------------|
| URL:      | /m1/likes/{id}    |
| METHOD:	| GET               |

### LIKE/DISLIKE A MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /m1/likes/                              |
| METHOD:	| POST                                    |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | {"movieId":"{id}", "username":"{username}"}      |

## Rental and Buying:

### RENT A MOVIE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /r1/rent/                              |
| METHOD:	| POST                                    |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | {"movieId":"{id}", "username":"{username}", "days":3,"copiesQty":1}|
 
- the rental Total will be the rentalPrice by number of days 


### GET LIST OF ACTIVE RENTS

| PROP      |    VALUE             |
|-----------	|----------------------|
| URL:      | /r1/rent/{username}  |
| METHOD:	| GET                  |

### REQUEST MOVIE RETURN

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /r2/rent/                              |
| METHOD:	| PUT                                    |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | {"movieId":"{id}", "username":"{username}"}|

it returns the 
- overtimeDays
- amountToPay

### CONFIRM PAYMENT AND RETURN MOVIE 
 
| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /r3/rent/                              |
| METHOD:	| PUT                                    |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | {"movieId":"{id}", "username":"{username}"}|


### REQUEST MOVIE PURCHASE

| PROP      |                    VALUE                |
|-----------	|-----------------------------------------|
| URL:	    | /b1/buy/                              |
| METHOD:	| POST                                    |
| HEADER:	| Content-Type:application/json           |
| BODY:	    | {"movieId":"{id}", "username":"{username}", "copiesQty":1}|

it returns the:
- movieId for displaying info
- copiesQty for confirmation
- amountToPay 

### CONFIRM MOVIE PURCHASE

| PROP      |                    VALUE                  |
|-----------	|-------------------------------------------|
| URL:	    | /b2/buy/                                  |
| METHOD:	| PUT                                       |
| HEADER:	| Content-Type:application/json             |
| BODY:	    | {"movieId":"{id}", "username":"{username}" |


### GET MOVIE PURCHASE HISTORY BY USERNAME 

| PROP      |    VALUE             |
|-----------	|----------------------|
| URL:      | /b1/buy/{username}   |
| METHOD:	| GET                  |


