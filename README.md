# CoopCredit System

![Module 6 Compliant](https://img.shields.io/badge/Module%206-Compliant-success)
![Java 21](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green)
![Tests](https://img.shields.io/badge/Tests-Passing-success)

A comprehensive, production-ready backend system for credit application management, built with Java 21, Spring Boot, and Hexagonal Architecture.

## 🏗️ Architecture

The system follows a **Hexagonal Architecture** (Ports and Adapters) pattern to ensure separation of concerns and maintainability.

### 📐 Architecture Diagrams

- **[Hexagonal Architecture](docs/architecture-hexagonal.md)** - Complete view of layers, ports, and adapters
- **[Use Cases](docs/use-cases.md)** - Actors, use cases, and access control matrix
- **[Microservices Architecture](docs/microservices-architecture.md)** - Service communication and deployment

### Microservices

1. **credit-application-service** (Port 8080)
   - Core domain service handling business logic and persistence
   - JWT authentication and authorization
   - Credit application workflow orchestration
   - Integration with risk evaluation service

2. **risk-central-mock-service** (Port 8081)
   - Simulates external credit risk evaluation
   - Deterministic score generation (300-950 range)
   - Risk level classification (CRITICAL, HIGH, MEDIUM, LOW)

### Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.0
- **Database**: MySQL 8.0
- **Migrations**: Flyway
- **Security**: Spring Security + JWT (Stateless)
- **Mapping**: MapStruct
- **Testing**: JUnit 5, Mockito, Testcontainers
- **Observability**: Actuator + Prometheus
- **Documentation**: Swagger/OpenAPI 3.0
- **Containerization**: Docker & Docker Compose

## 🚀 Getting Started

### Prerequisites

- Docker and Docker Compose installed
- Java 21 (optional, if running locally without Docker)
- Maven (optional, wrapper included)

### Running with Docker (Recommended)

1. Clone the repository
2. Navigate to the project root
3. Run the system:
   ```bash
   docker-compose up --build
   ```
4. The services will be available at:
   - **Credit Service**: `http://localhost:8080`
   - **Risk Mock**: `http://localhost:8081`
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - **Actuator Health**: `http://localhost:8080/actuator/health`
   - **Prometheus Metrics**: `http://localhost:8080/actuator/prometheus`

### Running Locally

1. Start MySQL (ensure port 3306 is available):
   ```bash
   docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=coopcredit_db mysql:8.0
   ```

2. Run Risk Central Mock:
   ```bash
   cd risk-central-mock-service
   ./mvnw spring-boot:run
   ```

3. Run Credit Application Service:
   ```bash
   cd credit-application-service
   ./mvnw spring-boot:run
   ```

## 🔑 Security & Roles

The system is secured via **JWT (stateless)**. You must authenticate to access protected endpoints.

### Default Users (Created by Migration V3)

| Role | Username | Password | Access |
|------|----------|----------|--------|
| **ADMIN** | `admin` | `admin123` | Full system access |
| **ANALYST** | `analyst` | `analyst123` | View & evaluate applications |

### Public Endpoints

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - Authentication (returns JWT token)

### Role-Based Access Control

- **AFILIADO**: Create and view own credit applications
- **ANALISTA**: View and evaluate pending applications
- **ADMIN**: Full access to all resources

## 📦 API Endpoints

### Authentication

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Affiliates

- `POST /api/affiliates` - Register a new affiliate (Admin only)
- `GET /api/affiliates/{id}` - Get affiliate details (Admin, Analyst)
- `GET /api/affiliates` - List all affiliates (Admin, Analyst)
- `PUT /api/affiliates/{id}` - Update affiliate (Admin only)

### Credit Applications

- `POST /api/credit-applications` - Create new credit application (Afiliado, Admin)
- `GET /api/credit-applications` - List applications (filtered by role)
- `GET /api/credit-applications/{id}` - Get application details
- `PUT /api/credit-applications/{id}/status` - Update status (Analyst, Admin)

### Risk Evaluation (Mock Service)

- `POST /api/risk-evaluation` - Evaluate credit risk (internal use)

## ✅ Business Rules & Validations

### Affiliate Registration

- ✅ Document number must be unique
- ✅ Monthly salary must be > 0
- ✅ Affiliate must be ACTIVE to apply for credit

### Credit Application

- ✅ Affiliate seniority ≥ 6 months
- ✅ Requested amount ≤ 10x monthly salary
- ✅ Term between 6-60 months
- ✅ Debt-to-income ratio ≤ 40%

### Risk Evaluation

- **Score Range**: 300-950 (deterministic per document)
- **Classification**:
  - < 300: CRITICAL → Auto-reject
  - 300-500: HIGH → Manual review required
  - 501-700: MEDIUM → Approved with 80% of requested amount
  - 701+: LOW → Approved for full amount

## 🧪 Testing

Comprehensive test suite with unit and integration tests.

### Quick Start

```bash
cd credit-application-service

# Run all tests
./mvnw clean test

# Run only unit tests
./mvnw test -Dtest="*UseCaseTest"

# Run only integration tests
./mvnw test -Dtest="*IntegrationTest"
```

### Test Coverage

- **Unit Tests**: Business logic with Mockito mocks
- **Integration Tests**: Full flows with Testcontainers (MySQL)
- **Test Cases**: 15+ tests covering all business rules

📖 **[Complete Testing Guide](docs/TESTING.md)**

## 📊 Observability

Health and metrics are exposed via **Spring Boot Actuator**:

- **Health Check**: `http://localhost:8080/actuator/health`
- **Metrics**: `http://localhost:8080/actuator/metrics`
- **Prometheus**: `http://localhost:8080/actuator/prometheus`
- **Info**: `http://localhost:8080/actuator/info`

### Structured Logging

All logs are structured in JSON format with:
- Timestamp
- Log level
- Logger name
- Message
- Trace ID (for distributed tracing)

## 📊 Monitoring & Observability

CoopCredit includes a complete observability stack with Prometheus and Grafana.

### Prometheus (Metrics Collection)

- **URL**: http://localhost:9090
- **Targets**: http://localhost:9090/targets
- **Scrape Interval**: 15 seconds
- **Retention**: 15 days

**Metrics Available**:
- JVM metrics (heap, threads, GC)
- HTTP request metrics (rate, duration, errors)
- Database connection pool (HikariCP)
- Custom business metrics

### Grafana (Visualization)

- **URL**: http://localhost:3000
- **Username**: `admin`
- **Password**: `admin`
- **Dashboard**: CoopCredit - Microservices Monitoring

**Dashboard Panels**:
1. HTTP Request Rate (both services)
2. JVM Heap Memory Usage
3. HTTP Request Duration (p95)
4. JVM Threads
5. Database Connection Pool
6. Risk Service Metrics

### Quick Queries

```promql
# Request rate
rate(http_server_requests_seconds_count[1m])

# Error rate
sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m]))

# Average response time
rate(http_server_requests_seconds_sum[1m]) / rate(http_server_requests_seconds_count[1m])

# Database connection pool utilization
(hikaricp_connections_active / hikaricp_connections_max) * 100
```

For detailed monitoring guide, see **[MONITORING.md](docs/MONITORING.md)**

## 📚 Documentation

- **[Architecture - Hexagonal](docs/architecture-hexagonal.md)** - Ports & Adapters pattern
- **[Use Cases](docs/use-cases.md)** - Business flows and actors
- **[Microservices](docs/microservices-architecture.md)** - Service communication
- **[Testing Guide](docs/TESTING.md)** - How to run and write tests
- **[Monitoring Guide](docs/MONITORING.md)** - Prometheus & Grafana setup
- **[Swagger UI](http://localhost:8082/swagger-ui.html)** - Interactive API documentation
- **[Postman Collection](coopcredit-postman-collection.json)** - API testing collection

## 🐳 Docker Deployment

The system uses multi-stage Dockerfiles for optimized images:

```bash
# Build and run all services
docker-compose up --build

# Run in detached mode
docker-compose up -d

# View logs
docker-compose logs -f credit-application-service

# Stop all services
docker-compose down

# Clean up volumes
docker-compose down -v
```

### Services

- **mysql**: MySQL 8.0 database (port 3306)
- **risk-central-mock**: Risk evaluation service (port 8081)
- **credit-application-service**: Main application (port 8080)

## 🎯 Module 6 Compliance

This project implements all Module 6 requirements:

- ✅ **Hexagonal Architecture**: Pure domain, ports, adapters
- ✅ **JPA Advanced**: Entity relationships, N+1 prevention, @EntityGraph
- ✅ **Security JWT**: Stateless authentication, role-based access
- ✅ **Observability**: Actuator, Prometheus metrics, structured logs
- ✅ **Microservices**: Two services with REST communication
- ✅ **Docker**: Multi-stage builds, docker-compose orchestration
- ✅ **Testing**: Unit tests (Mockito), Integration tests (Testcontainers)
- ✅ **Validation**: Bean Validation, cross-field validations
- ✅ **Error Handling**: RFC 7807 ProblemDetail with timestamp and traceId
- ✅ **Flyway**: Database migrations (V1, V2, V3)
- ✅ **MapStruct**: DTO mapping

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `./mvnw clean test`
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

---

**Built with ❤️ for CoopCredit**

*Generated by Antigravity Agent*