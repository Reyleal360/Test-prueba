# Testing Guide - CoopCredit System

This document provides comprehensive instructions for running and understanding the test suite.

## Test Structure

```
src/test/java/
├── com/coopcredit/application/
│   ├── application/usecases/          # Unit Tests
│   │   ├── affiliates/
│   │   │   └── RegisterAffiliateUseCaseImplTest.java
│   │   └── applications/
│   │       └── RegisterCreditApplicationUseCaseImplTest.java
│   └── infrastructure/integration/    # Integration Tests
│       ├── BaseIntegrationTest.java
│       └── CreditApplicationFlowIntegrationTest.java
```

## Test Categories

### 1. Unit Tests (JUnit 5 + Mockito)

**Purpose:** Test business logic in isolation without external dependencies

**Location:** `src/test/java/.../application/usecases/`

**Characteristics:**
- Fast execution (milliseconds)
- No database required
- No Spring context
- Mock all dependencies using Mockito

**Example:**
```java
@ExtendWith(MockitoExtension.class)
class RegisterCreditApplicationUseCaseImplTest {
    @Mock
    private AffiliateRepositoryPort affiliateRepositoryPort;
    
    @InjectMocks
    private RegisterCreditApplicationUseCaseImpl useCase;
    
    @Test
    void shouldRegisterCreditApplicationSuccessfully() {
        // Arrange
        when(affiliateRepositoryPort.findById(anyLong()))
            .thenReturn(Optional.of(validAffiliate));
        
        // Act
        CreditApplication result = useCase.register(1L, validApplication);
        
        // Assert
        assertThat(result).isNotNull();
        verify(affiliateRepositoryPort).findById(1L);
    }
}
```

---

### 2. Integration Tests (Spring Boot Test + Testcontainers)

**Purpose:** Test complete flows with real database and Spring context

**Location:** `src/test/java/.../infrastructure/integration/`

**Characteristics:**
- Slower execution (seconds)
- Real MySQL database via Testcontainers
- Full Spring context loaded
- Tests REST endpoints with MockMvc

**Example:**
```java
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class CreditApplicationFlowIntegrationTest extends BaseIntegrationTest {
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateAndEvaluateCreditApplication() throws Exception {
        // Test complete flow with real database
    }
}
```

---

## Running Tests

### Run All Tests

```bash
cd credit-application-service
./mvnw clean test
```

**Expected Output:**
```
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

### Run Only Unit Tests

```bash
./mvnw test -Dtest="*UseCaseTest"
```

**Tests Executed:**
- `RegisterAffiliateUseCaseImplTest` (5 tests)
- `RegisterCreditApplicationUseCaseImplTest` (10 tests)

**Execution Time:** ~2-3 seconds

---

### Run Only Integration Tests

```bash
./mvnw test -Dtest="*IntegrationTest"
```

**Tests Executed:**
- `CreditApplicationFlowIntegrationTest`

**Execution Time:** ~15-20 seconds (includes Docker container startup)

---

### Run Specific Test Class

```bash
./mvnw test -Dtest=RegisterCreditApplicationUseCaseImplTest
```

---

### Run Specific Test Method

```bash
./mvnw test -Dtest=RegisterCreditApplicationUseCaseImplTest#shouldRegisterCreditApplicationSuccessfully
```

---

### Run with Coverage Report

```bash
./mvnw clean test jacoco:report
```

**Report Location:** `target/site/jacoco/index.html`

---

## Test Cases Overview

### RegisterAffiliateUseCaseImplTest

| Test Case | Description | Expected Result |
|-----------|-------------|-----------------|
| `shouldRegisterAffiliateSuccessfully` | Valid affiliate registration | Success |
| `shouldRejectWhenDocumentNumberExists` | Duplicate document | BusinessRuleException |
| `shouldRejectWhenSalaryIsZero` | Salary = 0 | BusinessRuleException |
| `shouldRejectWhenSalaryIsNegative` | Salary < 0 | BusinessRuleException |
| `shouldCalculateSeniorityCorrectly` | Seniority calculation | Correct months |

---

### RegisterCreditApplicationUseCaseImplTest

| Test Case | Description | Expected Result |
|-----------|-------------|-----------------|
| `shouldRegisterCreditApplicationSuccessfully` | Valid application | APPROVED status |
| `shouldRejectWhenAffiliateNotFound` | Invalid affiliate ID | NotFoundException |
| `shouldRejectWhenAffiliateNotActive` | Inactive affiliate | BusinessRuleException |
| `shouldRejectWhenInsufficientSeniority` | Seniority < 6 months | BusinessRuleException |
| `shouldRejectWhenAmountExceedsSalaryLimit` | Amount > 10x salary | BusinessRuleException |
| `shouldRejectWhenTermTooShort` | Term < 6 months | BusinessRuleException |
| `shouldRejectWhenTermTooLong` | Term > 60 months | BusinessRuleException |
| `shouldRejectWhenDebtToIncomeRatioTooHigh` | Ratio > 40% | BusinessRuleException |
| `shouldSetInReviewForHighRisk` | Score 300-500 | IN_REVIEW status |
| `shouldApproveWithConditionsForMediumRisk` | Score 501-700 | APPROVED with 80% amount |

---

## Testcontainers Setup

### What is Testcontainers?

Testcontainers is a Java library that provides lightweight, throwaway instances of databases, message brokers, and other services for integration testing.

### Configuration

**BaseIntegrationTest.java:**
```java
@Testcontainers
public abstract class BaseIntegrationTest {
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("test_db")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}
```

### Requirements

- Docker must be running
- Docker daemon accessible
- Sufficient disk space for MySQL image (~500MB)

### First Run

On first execution, Testcontainers will:
1. Download MySQL 8.0 Docker image
2. Start container
3. Run Flyway migrations
4. Execute tests
5. Stop and remove container

**Note:** Subsequent runs are faster as the image is cached.

---

## Troubleshooting

### Issue: Tests fail with "Cannot connect to Docker daemon"

**Solution:**
```bash
# Start Docker
sudo systemctl start docker

# Verify Docker is running
docker ps
```

---

### Issue: Tests fail with "Port already in use"

**Solution:**
```bash
# Find process using port
lsof -i :3306

# Kill the process or stop MySQL
sudo systemctl stop mysql
```

---

### Issue: Flyway migration errors

**Solution:**
```bash
# Clean and rebuild
./mvnw clean
./mvnw test
```

---

### Issue: Out of memory during tests

**Solution:**
```bash
# Increase Maven memory
export MAVEN_OPTS="-Xmx1024m"
./mvnw test
```

---

## Best Practices

### 1. Test Naming Convention

```java
// Pattern: should[ExpectedBehavior]When[StateUnderTest]
@Test
void shouldRejectWhenAffiliateNotActive() { }

@Test
void shouldCalculateMonthlyQuotaCorrectly() { }
```

### 2. AAA Pattern (Arrange-Act-Assert)

```java
@Test
void testExample() {
    // Arrange - Set up test data and mocks
    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    
    // Act - Execute the method under test
    Result result = useCase.execute(1L);
    
    // Assert - Verify the outcome
    assertThat(result).isNotNull();
    verify(repository).findById(1L);
}
```

### 3. Use AssertJ for Fluent Assertions

```java
// Good
assertThat(result.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
assertThat(result.getMonthlyQuota()).isGreaterThan(BigDecimal.ZERO);

// Avoid
assertEquals(ApplicationStatus.APPROVED, result.getStatus());
```

### 4. Mock Only External Dependencies

```java
// Mock repositories (external)
@Mock
private AffiliateRepositoryPort affiliateRepository;

// Don't mock domain models or value objects
Affiliate affiliate = Affiliate.builder()
    .documentNumber("123")
    .build();
```

---

## Continuous Integration

### GitHub Actions Example

```yaml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
      - name: Run tests
        run: ./mvnw clean test
      - name: Upload coverage
        uses: codecov/codecov-action@v3
```

---

## Test Coverage Goals

| Layer | Target Coverage | Current |
|-------|----------------|---------|
| Domain (Use Cases) | 90%+ | ✅ |
| Application | 80%+ | ⚠️ |
| Infrastructure | 70%+ | ⚠️ |
| Overall | 80%+ | ⚠️ |

---

## Adding New Tests

### 1. Create Test Class

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("My Use Case Tests")
class MyUseCaseImplTest {
    
    @Mock
    private DependencyPort dependency;
    
    @InjectMocks
    private MyUseCaseImpl useCase;
    
    @BeforeEach
    void setUp() {
        // Initialize test data
    }
    
    @Test
    @DisplayName("Should do something when condition")
    void shouldDoSomethingWhenCondition() {
        // Test implementation
    }
}
```

### 2. Run New Tests

```bash
./mvnw test -Dtest=MyUseCaseImplTest
```

### 3. Verify Coverage

```bash
./mvnw clean test jacoco:report
open target/site/jacoco/index.html
```

---

## Resources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Testcontainers Documentation](https://www.testcontainers.org/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
