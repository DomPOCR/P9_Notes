# MEDISCREEN APP: ASSESSMENT

Micro-service assessment evaluate a level of risk (diabet) for a patient. This micro-service need patient and note to calculate the risk.

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
### Get Assessment by Patient Id
-   GET http://assessment:8083/assessment/id?id=PatientId
### Get Assessment by Family Name
- GET http://assessment:8083/assessment/familyname?familyName=name_of_family

# Architecture
![Alt Text](/Archi.png)



