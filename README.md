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
- Added 2 mongo replica sets for stock consistency

## Tests

- As a minimum 50% coverage Requirement of Task, only written tests enough to pass 50%

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