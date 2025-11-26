# CanBagi

CanBagi is a blood-donation matching platform that connects donors and hospitals using intelligent matching algorithms and real-time notifications. It digitalizes the donation workflow and ensures timely alerts during emergencies.

## Key Features
- Donor and hospital registration and profiles
- Intelligent matching by blood type, location and availability
- Emergency broadcast notifications to matched donors
- Role-based access control (admin, hospital, donor)
- Audit logging and basic analytics

## Tech Stack
- Language: Java
- Framework: Spring Boot
- Build: Maven
- Database: PostgreSQL (recommended)
- Notifications: FCM / SMTP (configurable)
- Containerization: Optional Docker support

## Architecture (high level)
- REST API implemented with Spring Boot
- Service layer for business logic and matching algorithms
- Repository layer using Spring Data JPA
- Asynchronous notification subsystem for emergency alerts

## Requirements
- JDK 17+
- Maven 3.6+
- PostgreSQL (or other JDBC-compatible DB)
- (Optional) Docker and Docker Compose

## Quick start (Windows)
1. Configure database and environment variables (see `Configuration`) 
2. Build the project:
   ```bash
   mvn clean package
   ```
   
3.Configuration
Set configuration in `src/main/resources/application.yml` or via environment variables. Common variables:
- `SPRING_DATASOURCE_URL` — JDBC URL (e.g. `jdbc:postgresql://localhost:5432/canbagi`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `FCM_SERVER_KEY` — Firebase Cloud Messaging server key (for push)
- `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD` — SMTP for emails

Example minimal `application.properties` snippet:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/canbagi
spring.datasource.username=canbagi
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
server.port=8080CanBagi connects donors and hospitals using intelligent matching algorithms, digitalizing the blood donation process. In emergencies, it ensures timely notifications reach the right donor, saving precious seconds.
