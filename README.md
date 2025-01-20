# EduConnect API

This project was created as part of the Java Backend module at Lab365, SENAI - Florianópolis, SC. It was developed in Java with Spring Boot to manage educational resources, and uses JDK 17 and a PostgreSQL database with a specified schema.

The EduConnect application is an educational management system that offers a RESTful API to facilitate the administration of educational institutions. It allows managing students, teachers, courses, grades, and other education-related information. Through the API, users can perform operations such as creating, updating, retrieving, and deleting data stored in the database, as well as authenticating users and controlling access to endpoints based on their roles and permissions.

## Autora

I am Barbara Calderon, a software developer.
- [Github](https://www.github.com/barbaracalderon)
- [Linkedin](https://www.linkedin.com/in/barbaracalderondev/?locale=en_US)
- [Twitter](https://www.x.com/bederoni)

# Table of Contents

1. [Kanban and Trello](#1-kanban-and-trello)  
2. [Technologies Used](#2-technologies-used)  
3. [Project Folder Structure](#3-project-folder-structure)  
4. [Routes and Authorizations](#4-routes-and-authorizations)  
5. [Installation](#5-installation)  
6. [Step-by-Step Usage](#6-step-by-step-usage)  
7. [Endpoint Descriptions](#7-endpoint-descriptions)  
8. [Single Responsibility Principle](#8-single-responsibility-principle)  
9. [Gitflow and Development](#9-gitflow-and-development)  
10. [Future Enhancements](#10-future-enhancements)  
11. [Final Considerations](#final-considerations)  

## 1. Kanban and Trello

The project was developed using a [public Kanban dashboard on Trello](https://trello.com/b/FR67nTgH/fmt-m%C3%B3dulo-1-projeto-avaliativo).  

An Insomnia export file for testing (`educonnect.json`) is included in the project.

## 2. Technologies Used

Below is the Maven project configuration.

- **Spring Boot**: Framework for developing Java applications based on Spring.
- **Spring Boot Starter Data JPA**: Support for using Spring Data JPA for data persistence.
- **Spring Boot Starter Security**: Integration of Spring Security for authentication and authorization.
- **Spring Boot Starter Web**: Configuration for developing web applications with Spring MVC.
- **Spring Boot DevTools**: Development tools for automatic reload and other features during development.
- **Spring Boot Starter Data JDBC**: Support for using Spring Data JDBC.
- **PostgreSQL JDBC Driver**: For connecting to the PostgreSQL database.
- **Flyway Core**: Tool for database version control and migrations.
- **Jakarta Validation API**: Validation API for Java object validation.
- **Java JWT**: Library for creating and verifying JWT (JSON Web Tokens).
- **Lombok**: Library that simplifies the creation of Java classes by reducing boilerplate code.

## 3. Project Folder Structure

The project was designed to follow a folder organization divided into: controllers (+dtos), datasource (entities and repositories), infra (exceptions and security), interfaces, and services.

![Folder Structure](educonnect_estrutura_de_pastas.png)

## 4. Routes and Authorizations

First, the database must be populated with the roles available for this application.

![Roles in the 'papel' table in the DB](educonnect_papeis.png)

When registering a user, you must specify the role ID that exists in the database. For example, when creating an ADMIN user, the role ID (`id_papel`) should be set to 1, and so on. This is important because no other roles will be accepted. The application uses this role assignment during registration to check route authorizations (`getAuthorities()`). For instance, a student user does not have access to teacher-related routes.

**Important Note**: Since the application starts with an empty database, it is not feasible to allow only ADMIN users to register others because the ADMIN user does not exist initially. Therefore, this route is open to everyone (`permitAll()`).



## 5. Installation

### a) Clone the Repository

```
git clone git@github.com:barbaracalderon/fmt-educonnect.git
```

### b) Configure the Database

When starting the application, the tables are automatically created in the database, but they will be empty. Below are the queries to create the `papel` table and populate it with Enum values:


```sql
CREATE TABLE papel (
    id SERIAL PRIMARY KEY,
    nome_papel VARCHAR(20) NOT NULL
);
```

```sql
INSERT INTO papel (nome_papel) VALUES 
('ADMIN'),
('PROFESSOR'),
('PEDAGOGICO'),
('RECRUITER'),
('ALUNO');
```

### c) Configure the application.properties file

```properties
spring.application.name=educonnect
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/educonnect
spring.datasource.username=[your-password-here]
spring.datasource.password=[your-password-here]
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.flyway.baseline-on-migrate=true
spring.flyway.schemas=public
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

### d) Run the application

Navigate to the project directory and run the application with the following command:

```bash
./mvnw spring-boot:run
```

The server will start at http://localhost:8080/

## 6. Step-by-step usage

With the server running and the database created, and after populating the `Papel` table with Enum values, all tables must be populated to test the functionality of all endpoints with their corresponding HTTP methods. Remember, the first user registration should be for an ADMIN user.

### - Registering an ADMIN User

Registering an ADMIN user is essential. Once registered via the POST `/cadastro` endpoint, the user will exist in the system.

### - Logging into the System

After registering, it is possible to log in through the POST `/login` endpoint. If the password verification is successful, a token will be provided as a response. This token must be copied and used in the request headers as a Bearer Token. From this point on, using the token is mandatory for every request, or a **403 Forbidden** status code will be returned, indicating access to the route is denied.

It is important to note that during registration, an authorization type is assigned to the user based on the `idPapel` provided. Each endpoint's access is either granted or restricted according to the user’s role. This authorization/restriction scheme based on roles is defined in the `SecurityConfigurations` file (access permissions) and the `CadastroEntity` file (authorization assignment).

### - Endpoint Sequence

The order of endpoint calls must be followed to populate the database correctly, as the tables have relationships and dependencies. For instance, creating a teacher (`Docente`) requires a `idCadastro`, meaning the user must already be registered in the system. Once the user exists, a teacher can be created by providing the user’s `idCadastro`.

The sequence of endpoints for data manipulation and persistence is as follows:

1. `/cadastro`
2. `/login`
3. `/docentes`
4. `/cursos`
5. `/materias`
6. `/turmas`
7. `/alunos`
8. `/notas`

A detailed description of each route, its HTTP method, request JSON, response JSON (or exception message) is provided later in the documentation.


## 7. Endpoint descriptions

| Authorization                    | HTTP Method | Endpoint               | Request Body (Example)                                                                            |
|--------------------------------|-------------|------------------------|---------------------------------------------------------------------------------------------------|
| `permitAll()`                  | GET         | /cadastro              | ```{"nome": "Maria Silva", "login": "maria", "password": "1234", "idPapel": 1}```                 |
| `permitAll()`                  | POST        | /login                 | ```{"login": "maria", "password": "1234"}```                                                      | 
| `ADMIN, PEDAGOGICO, RECRUITER` | POST        | /docentes              | ```{"nome": "Maria Silva", "dataEntrada": "01-01-2001", "idCadastro": 1}```                       |
| `ADMIN, PEDAGOGICO, RECRUITER` | GET         | /docentes              |                                                                                                   |
| `ADMIN, PEDAGOGICO, RECRUITER` | GET         | /docentes/{id}         |                                                                                                   |
| `ADMIN, PEDAGOGICO, RECRUITER` | PUT         | /docentes/{id}         | ```{"nome": "Marina Silva", "dataEntrada": "05-05-2005", "idCadastro": 1}```                      |
| `ADMIN, PEDAGOGICO`            | POST        | /cursos                | ```{"nome": "Filosofia", "dataEntrada": "04-08-2019"}```                                          |
| `ADMIN, PEDAGOGICO`            | GET         | /cursos                |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | GET         | /cursos/{id}           | ```{"nome": "Filosofia", "dataEntrada": "04-08-2019"}```                                          |
| `ADMIN, PEDAGOGICO`            | PUT         | /cursos/{id}           | ```{"nome": "Sistemas de Informação", "dataEntrada": "04-08-2019"}```                             |
| `ADMIN`                        | DELETE      | /cursos/{id}           |                                                                                                   |
| `ADMIN`                        | POST        | /materias              | ```{"nome": "Engenharia de Software", "dataEntrada": "04-08-2019",	"idCurso": 2}```               |
| `ADMIN`                        | GET         | /materias              |                                                                                                   |
| `ADMIN`                        | GET         | /materias/{id}         |                                                                                                   |
| `ADMIN`                        | GET         | /materias/cursos/{id}  |                                                                                                   |
| `ADMIN`                        | PUT         | /materias/{id}         | ```{"nome": "Compiladores", "dataEntrada": "04-08-2019"}```                                       |
| `ADMIN`                        | DELETE      | /materias/{id}         |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | POST        | /turmas                | ```{"nome": "Turma 2022.2", "dataEntrada": "11-08-2022", "idDocente": 5, "idCurso": 4}```         |
| `ADMIN, PEDAGOGICO`            | GET         | /turmas                |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | GET         | /turmas/{id}           |                                                                                                   |
| `ADMIN, PEDAGOGICO`            | PUT         | /turmas/{id}           | ```{"nome": "Turma 2024.1", "dataEntrada": "11-08-2022", "idDocente": 2, "idCurso": 2}```         |
| `ADMIN`                        | DELETE      | /turmas/{id}           |                                                                                                   |
| `ADMIN`                        | POST        | /alunos                | ```{"nome": "Aluno A", "dataNascimento": "01-01-1997", "idCadastro": 17, "idTurma": 1}```         |
| `ADMIN`                        | GET         | /alunos                |                                                                                                   |
| `ADMIN`                        | GET         | /alunos/{id}           |                                                                                                   |
| `ADMIN, ALUNO`                 | GET         | /alunos/{id}/notas     |                                                                                                   |
| `ADMIN, ALUNO`                 | GET         | /alunos/{id}/pontuacao |                                                                                                   |
| `ADMIN`                        | PUT         | /alunos/{id}           | ```{"nome": "Aluno B", "dataNascimento": "04-08-1990"}```                                         |
| `ADMIN`                        | DELETE      | /alunos/{id}           |                                                                                                   |
| `ADMIN, PROFESSOR`             | POST        | /notas                 | ```{"dataLancamento": "01-01-2021", "idAluno": 4, "idDocente": 4, "idMateria": 3, "valor": 50}``` |
| `ADMIN, PROFESSOR`             | GET         | /notas                 |                                                                                                   |
| `ADMIN, PROFESSOR`             | GET         | /notas/{id}            |                                                                                                   |
| `ADMIN, PROFESSOR, ALUNO`      | GET         | /notas/aluno/{id}      |                                                                                                   |
| `ADMIN, PROFESSOR`             | PUT         | /notas/{id}            | ```{"dataLancamento": "01-01-2001", "idAluno": 3, "idDocente": 2, "idMateria": 1, "valor": 75}``` |
| `ADMIN`                        | DELETE      | /alunos/{id}           |                                                                                                   |

### Logs

The application includes logging functionality (`@slf4j`) to display `info` and `error` messages in the terminal.

## 8. Single Responsibility Principle

The project follows the **Single Responsibility Principle (SRP)**, a core concept in Object-Oriented Programming, defined by Robert C. Martin. Each class is designed to handle only one responsibility.

In this project, SRP was applied as follows:

- **Controllers**: Handle HTTP requests and delegate processing to service classes, returning appropriate responses to the client.
- **Services**: Implement the application's business logic, performing validations, calculations, and data access through communication with repository classes. Each service communicates only with its respective repository (e.g., Service "A" interacts with Repository "A") or other service classes, not with other repositories directly.
- **Repositories**: Provide methods to access and manipulate database records, sometimes with custom methods extending `JpaRepository`.
- **DTOs (Data Transfer Objects)**: Transfer data between layers, handling only data attributes and accessor methods without business logic.
- **Interfaces**: Define contracts for service classes, specifying methods for a specific area of functionality.

## 9. Gitflow and Development

This project follows the **Gitflow** branching model for version control:

- **Main**: Stable, production-ready code.
- **Release**: Stable code ready for execution, with version numbers in the format `V.0.0.0`.
- **Develop**: Main development branch where features are integrated.
- **Features**: Branches for individual feature development (`feat`).
- **Fixes**: Branches for bug fixes.
- **Hotfix**: Emergency fixes applied to the production `main` branch.

## 10. Future Enhancements

Potential future improvements for this project:

- **Automated Testing**: Implement unit tests using JUnit and Mockito.
- **Swagger OpenAPI Documentation**: Adopt standardized API documentation with OpenAPI.
- **Dockerization**: Add a `Dockerfile` to define the runtime environment and `docker-compose.yml` for container orchestration, simplifying deployment.
- **Monitoring**: Integrate monitoring tools like Grafana to track application performance metrics.

## Final Considerations

This project was developed individually as part of the Java Backend Module 1 assessment.

Best regards,  

Barbara Calderon.