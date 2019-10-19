# Booking room application #

It is simple application which allow to manage booking rooms
There are multiple implementations of databases.

## Tech/frameworks used ##

<img src="https://whirly.pl/wp-content/uploads/2017/05/spring.png" width="200"><img src="http://yaqzi.pl/wp-content/uploads/2016/12/apache_maven.png" width="200"><img src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Mockito_Logo.png" width="200"><img src="https://jules-grospeiller.fr/media/logo_competences/lang/json.png" width="200"><img src="http://www.postgresqltutorial.com/wp-content/uploads/2012/08/What-is-PostgreSQL.png" width="200"><img src="https://cdn.bulldogjob.com/system/readables/covers/000/001/571/thumb/27-02-2019.png" width="200"><img src="https://i2.wp.com/bykowski.pl/wp-content/uploads/2018/07/hibernate-2.png?w=300" width="200">

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
To test all possibilities of Booking API. You have to log in, configure login and password in [security.properties]:

```
spring.security.admin-name=yourLogin
spring.security.admin-password=yourPassword
```

## Setup Database ##

To change using database go to [application.properties]. You can choose in-memory or hibernate database
```
   com.be.booker.business.database=in-memory
   com.be.booker.business.database=hibernate
```
Application works correctly without hibernate database.

To use **hibernate** , firt  configure it on your computer, use PgAdmin ( or another tool) and [hibernate.properties]
```
spring.datasource.url=yourDatabase
spring.datasource.username=yourUserName
spring.datasource.password=yourPassword
```
