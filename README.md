# getir-case-study

## How to Run Application

- Simply run the docker image.
```
$ docker-compose -f docker-compose-mongo.yml up -d
$ mvn clean package -DskipTests
$ docker-compose build
$ docker-compose up -d
```
Then create customer:
http://localhost:8080/api/customer/signup

## Swagger:
- Available at http://localhost:8080/ by default

## Java Version

- OpenJDK 17 is used

## Database
- MongoDB
- Added 2 mongo replica sets

## Tests

- As a minimum 50% coverage Requirement of Task, only written tests enough to pass 50%

## Stock consistency
All we need to do to fix it is add 2 lines to the Account class:
```
@Version
private Long version;
```
- the application also reads the property annotated with @Version. For new objects, it will be 0. When it tries to update the record, it will search for the object with the same id and version. If no such record can be found, an exception will be thrown. And Spring will update the version number for you every time you save the new record to the database, so the application doesn’t need to change much. You don’t need to manually track all updates to the object and keep the version variable up to date.


## Documentation

- Can be found here
```
./Documentation.pdf
```

## Postman

- Can be found root of project.
```
./GetirCaseStudy.postman_collection.json
```