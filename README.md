# Booking room application #

1. Case description

Your are hired as a contractor by a small business owner to create a tool, that would allow his employees (around 30 people) to book conference rooms that are available in the office. This application should:
 - allow to manage available rooms
 - allow to manage users
 - allow booking room if it is available in particular time frame
 - present booking schedule for all rooms for particular time frame
 - present booking schedule for selected room for particular time frame
 - present booking schedule for a user (display all bookings done by person)
 
This tool should be installed on the server available in the office and allow access through the network. It should take HTTP calls and return JSON responses. There is no need for a frontend/GUI as it will be integrated with other system that belong to Your client. Your task is to design and implement solution, which will mostly consist of a bunch of REST services. RESTfull approach for designing services is preferred.

2. Functional requirements

 2.1. Managing usres
 System should allow a management of users stored in database.

 2.1.1. Creating new user 
 System should allow to create new user and store it in the database. The creation should be done by a REST call and JSON response.
 User entity should consist of:
  - name: text, max. length 50, required
  - surname: text, max. length 100, required
  - login: text, max. length 100, required, unique (allows to identify user)
  - password: text, max. length 100, min. length 6, required
  
 REST service should return a response about a success or an error, when provided data is missing, not correct or particular login already exists in the system. Proper validation on properties should be applied.
 Password should be stored in a secure way in database (prevent from unauthorized read)

 2.1.2. Editing existing user
 System should allow to edit user that exists in the database. The edit process should be done by a REST call and JSON response.
 The call should consist of user properties. The login is obligatory as it identifies the user entity. Other properties are optional and when they are missing, system should not change them in the database.
 System should return response with information about success or error (for example when user is missing)
 
 2.1.3. Deleting user
 System should allow to delete a user. The edit process should be done by a REST call and JSON response.
 The call should consist of login.
 System should return response with information about success or error (for example when user is missing) 
 
 2.1.4. Getting list of available users.
 System should return a list of all available users using a REST call and JSON response. The response should contains all user information, except for password.

 Access to the create/edit/delete users should be granted only by a secret password key. This key, may be kept on the server in a .properties files and it will be known only to system administrator. It should be verified when system receives a call. Getting list of users service should be available to everone.
 
2.2. Managing rooms
 System should allow a management of rooms stored in database.

 2.2.1. Creating new room 
 System should allow to create new room and store it in the database. The creation should be done by a REST call and JSON response.
 Room entity should consist of:
  - room name: text, max. length 50, required, unique (allows to identify room)
  - location description: text, max. length 256, optional
  - number of seats: number, max. 100, required
  - projector: yes/no, optional, default: no
  - phone number: text, max. length 100, optional
  
 REST service should return a response about a success or an error, when provided data is missing, not correct or particular room name already exists in the system. Proper validation on properties should be applied.

 2.2.2. Editing existing room
 System should allow to edit room that exists in the database. The edit process should be done by a REST call and JSON response.
 The call should consist of room properties. The room name is obligatory as it identifies the room entity. Other properties are optional and when they are missing, system should not change them in the database.
 System should return response with information about success or error (for example when room is missing)
 
 2.2.3. Deleting room
 System should allow to delete a user. The edit process should be done by a REST call and JSON response.
 The call should consist of room name.
 System should return response with information about success or error (for example when room is missing) 
 
 2.2.4. Getting list of available rooms.
 System should return a list of all available rooms using a REST call and JSON response. There response should contain all properties that belong to room entity.

 Access to the create/edit/delete users should be granted only by a secret password key (as as with users). This key, may be kept on the server in a .properties files and it will be known only to system administrator. It should be verified when system receives a call. Getting list of users service should be available to everone.
 
 
 2.3 . Booking management
 
 2.3.1. Booking room
 System should allow to book a room for a particular time frame. The booking process should be done by a REST call and JSON response.
 The call should consist of room name, users login and password, date from and date to. All those properties are required.
 System should return response with information about success or error (if room is already booked, user login and password does not match, etc).
 
 2.3.2. Getting booking schedule for all rooms
 System should allow to get a list of all bookings for a particular time frame. The booking process should be done by a REST call and JSON response.
 The call should consist of a time frame (date from, date to). Time frame may be open, meaning that date from and/or date to may be empty providing no restriction for getting booking in the past and/or in the future.
 System should return response which contains list booking list. Each booking contains room name, user name, user surname, start date and end date.
 
 2.3.3. Getting booking schedule for single room
 System should allow to get a list of all bookings for room and particular time frame. The booking process should be done by a REST call and JSON response.
 The call should consist of a room name and time frame (date from, date to). Time frame may be open, meaning that date from and/or date to may be empty providing no restriction for getting booking in the past and/or in the future.
 System should return response which contains list booking list. Each booking contains room name, user name, user surname, start date and end date.
 
 2.3.4. Getting booking schedule for user
 System should allow to get a list of all bookings for user and particular time frame. The booking process should be done by a REST call and JSON response.
 The call should consist of a room name and time frame (date from, date to). Time frame may be open, meaning that date from and/or date to may be empty providing no restriction for getting booking in the past and/or in the future.
 System should return response which contains list booking list. Each booking contains room name, user name, user surname, start date and end date.
 
Comments about requirements
  - time frame may be open, meaning that date from and/or date to may be empty providing no restriction for getting booking in the past and/or in the future.
  - the following date format should be used in all calls: YYYY-MM-DD hh:mm:ss  
  - when describing properties and call arguments, "required" means, that the property cannot be empty. "optional" is the opposite. 
  - passwords should be stored in a secure way. How exactly? It is up to You to decide.
  - REST calls should be validated, meaning that an error response should be returned, when arguments do not follow data restrictions specified in requirements
 
3. Architectural requirements
 3.1. Implement service using Java programming language (version 8 or highier)
 3.2. Utilize frameworks such as Spring and Hibernate. Do not use full JEE. Solution should be deployable using Sprng Boot (recommended) or web container like Tomcat.
 3.3. Application should be easy to build using single commend (use Maven or Gradle)
 3.4. Use github.com or bitbucket.com for source code management. Use public repository.
 3.5. Create readme file that would describe how to build and deploy application.
 3.6. Create a documentation of available services
 3.7. For simplicity, use in-memory database like H2, HSQLDB or other. You do not need to store data when system reboots.
 3.8. Following important quality standards, solution should include automatic testing
 
4. Initial data
 
 The following data should be placed in database when the system is started.
 
 Users:
 Name		Surname			Login			Password
 John		Smith			jsmith			qwerty
 Jane		Doe				jdoe			mySecret
 
 Rooms:
 Name			Location		Seats		Projector		Phone number
 Large Room		1st floor		10			yes				22-22-22-22
 Medium Room	1st floor		6			yes				
 Small Room		2nd floor		4			no

## Instalation ##

* JDK 1.8
* Apache Maven 3.x

## Build and Run ##
```
mvn clean package:
mvn exec:java
```
## API ##

Application is available on localhost:[PORT]. Use ```http://localhost:[PORT]/swagger-ui.html#/booking-controller```
