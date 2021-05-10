# work-planning
Assignment:

Build a REST application from scratch that could serve as a work planning service.
Business requirements:

    A worker has shifts
    A shift is 8 hours long
    A worker never has two shifts on the same day
    It is a 24 hour timetable 0-8, 8-16, 16-24
    Preferably write a couple of units tests.

 
To build:
mvn clean package

To run unit tests:
mvn clean test

To run
mvn springboot:run

Health check endpoint:
http://localhost:8989/api/v1/health-check

Swagger: https://app.swaggerhub.com/apis/jgranadosiastic/work-planner/1.0.0

