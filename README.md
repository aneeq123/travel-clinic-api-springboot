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
```

The API will start on:

- http://localhost:8080


## H2 Database Console

This project uses an **H2 in-memory database**.

You can access the H2 web console at:

- http://localhost:8080/h2-console

Use the following settings:

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty, unless specified in application.properties)

Note:
Access to the H2 console is enabled via the `config` package, which includes:
- H2 console configuration
- Security configuration to allow console access


## API Testing (Postman)

You can test the API using **Postman** (or any REST client such as Insomnia).

If you do not have Postman installed:
- Download from: https://www.postman.com/downloads/

Base URL:

- http://localhost:8080


## Key Endpoints

### Clinics

- `POST /clinics` – Create a clinic  
- `GET /clinics` – List all clinics  
- `GET /clinics/{id}` – Get a clinic by ID  
- `PUT /clinics/{id}` – Update clinic  
- `DELETE /clinics/{id}` – Delete clinic  


### Appointments (Linked to Clinic)

- `POST /clinics/{id}/appointments` – Create appointment for a clinic  
- `GET /clinics/{id}/appointments?page=0&size=2` – Get paginated appointments  
- `PUT /clinics/{id}/appointments/{appointmentId}` – Update appointment  
- `DELETE /clinics/{id}/appointments/{appointmentId}` – Delete appointment  


## Pagination Example

```http
GET http://localhost:8080/clinics/1/appointments?page=0&size=2
GET http://localhost:8080/clinics/1/appointments?page=1&size=2

```

## Date Handling

Appointments use `LocalDateTime` in ISO-8601 format.

Example:

```text
2026-03-10T14:30:00
```

Example appointment request body:

```json
{
  "patientName": "Usman Ali",
  "patientEmail": "usman.ali@gmail.com",
  "vaccineType": "Typhoid",
  "appointmentDateTime": "2026-04-05T10:30:00"
}
```

The API supports optional date filtering using `from` and `to` query parameters.

Example:

```http
GET http://localhost:8080/clinics/1/appointments?from=2026-03-01T00:00:00&to=2026-03-31T23:59:59
```

Validation ensures:

- Correct ISO-8601 date format
- `from` date must be before `to` date
- Invalid dates return `400 Bad Request`

---

## Validation

Request validation is implemented using Spring Validation annotations.

Examples include:

- Required fields cannot be empty
- Email must be valid format
- Invalid requests return structured `400 Bad Request` responses

Validation errors are handled globally and returned in JSON format.

---

## Error Handling

The application uses a global exception handler located in the `exception` package.

Handled exceptions include:

- `ResourceNotFoundException` → 404 Not Found
- `MethodArgumentNotValidException` → 400 Bad Request
- `IllegalArgumentException` → 400 Bad Request

Example 404 response:

```json
{
  "timestamp": "2026-03-02T13:47:18",
  "status": 404,
  "error": "Not Found",
  "message": "Clinic not found: 999"
}
```

Example validation error response:

```json
{
  "timestamp": "2026-03-02T13:48:22",
  "status": 400,
  "error": "Validation failed",
  "fieldErrors": {
    "patientEmail": "must be a well-formed email address"
  }
}
```

This ensures:

- Consistent error response structure
- Clear messages for API consumers
- Separation of concerns between controller and error logic

---

## HTTP Status Codes Used

- `200 OK` – Successful GET or PUT
- `201 Created` – Resource successfully created
- `400 Bad Request` – Validation or input error
- `404 Not Found` – Resource does not exist

---

## Design Considerations

- Clear separation between controller, service, and persistence layers
- Global exception handling for consistent error responses
- Use of DTOs to prevent direct exposure of entity models
- H2 in-memory database for lightweight development and testing

This structure ensures the application is maintainable, scalable, and aligned with RESTful best practices.

## Author

Developed as part of an MSc assignment in Software Design with Cloud-Native Computing.






