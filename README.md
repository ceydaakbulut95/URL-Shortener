This project addresses the well-known design challenge of URL shortening. 
It generates short URLs by compressing Base64 using a ZooKeeper counter and UUID, resulting in a 7-character format. 
The entire application is containerized using Docker and developed in Java(openjdk:19-jdk).
Status: In Progress


Project structure is as below:

url_shortener/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ └── org/ │ │ │ └── example/ │ │ │ └── urlshortener/ │ │ │ ├── UrlShortenerApplication.java
│ │ │ ├── controller/ │ │ │ │ └── URLController.java
│ │ │ ├── exception/ │ │ │ │ ├── GlobalExceptionHandler.java
│ │ │ │ ├── URLNotFoundException.java
│ │ │ │ └── URLShorteningException.java │ │ │ ├── dto/ │ │ │ │ └── UrlRequestDTO.java
│ │ │ ├── model/ │ │ │ │ └── URLModel.java
│ │ │ ├── repository/ │ │ │ │ └── URLRepository.java
│ │ │ ├── service/ │ │ │ │ ├── KeyGenerationService.java │ │ │ │ ├── URLDatabaseService.java │ │ │ │ ├── URLEncodingService.java │ │ │ │ └── URLCleanupService.java
│ │ │ └── config/ │ │ │ └── ZooKeeperConfig.java
│ │ └── resources/ │ │ ├── application.properties
│ └── test/ │ ├── java/ │ │ └── org/ │ │ └── example/ │ │ └── urlshortener/ │ │ ├── controller/ │ │ │ └── URLControllerTest.java
│ │ ├── service/ │ │ │ ├── KeyGenerationServiceTest.java
│ │ │ └── URLCleanupServiceTest.java // Integration test - needs to be improved
│ │ └── repository/ │ │ └── URLRepositoryTest.java
│ └── resources/ ├── Dockerfile
├── Dockerfile-zookeeper ├── docker-compose.yml ├── pom.xml
├── README.Docker.md └── README.md

