# MEDISCREEN APP: NOTES

Micro-service NOTES manage patients notes on MEDISCREEN Application.

# Prerequisite to run
- Java 1.8
- Spring Boot 2.4.4
- Docker

# Installation
## Host file
- 127.0.0.1 patient
- 127.0.0.1 note
- 127.0.0.1 assessment
# Get Starting
Check Patient README.MD for global installation.
## EndPoint for global application :
- http://patient:8080

## URL 
### Get All notes
-   GET http://note:8082/patHistory/list
### Get note by Id
- GET http://note:8082/patHistory/id?id=noteId
### Get note by patient Id
- GET http://note:8082/patHistory/patient/id?id=PatientId
### Add note
- POST http://note:8082/patHistory/add
### Update note
- PUT http://note:8082/patHistory/update/id?id=noteId
### Delete note
- POST http://note:8082/patHistory/delete/id?id=noteId

# Architecture
![Alt Text](/Archi.png)



