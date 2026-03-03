# Travel Vaccination Clinic API (Spring Boot)

This project is a RESTful API for managing a Travel Vaccination Clinic.  
It supports creating clinics and managing vaccination appointments for travellers, including pagination, date handling, validation, and error responses.

## Technologies Used
- Java 17
- Spring Boot (Spring Web, Spring Data JPA, Validation)
- H2 In-Memory Database
- Maven
- Eclipse IDE (or any Java IDE)
- Postman (or similar API client) for testing

## Project Structure (Layered Architecture)
- `controller` – REST endpoints (HTTP requests/responses)
- `service` – business logic and validation
- `repository` – persistence layer (Spring Data JPA)
- `entity` – JPA entities (database tables)
- `dto` – Data Transfer Objects (request/response models)
- `exception` – custom exceptions + global exception handler
- `config` – Security configuration + H2 console configuration

## How to Run
### Option A: Run in Eclipse
1. Import the project as an **Existing Maven Project**
2. Ensure Java 17 is selected as the project JDK
3. Run: `TravelClinicApiApplication.java`

### Option B: Run from Terminal
From the project root (where `pom.xml` is):

```bash
./mvnw spring-boot:run
