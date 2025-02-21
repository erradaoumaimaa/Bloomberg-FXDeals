## **FX Deal Importer - Data Warehouse for Bloomberg**

A **Java-based FX deal importer** designed to validate, persist, and manage FX deal requests in a **PostgreSQL database**, using **Docker Compose** for easy deployment.

---

## **📋 Features**

- ✅ Validates FX deal data (currency codes, amounts, timestamps)
- 🚫 Prevents duplicate deals from being processed
- 🗄️ Persists valid deals to a PostgreSQL database
- ⚠️ Comprehensive error handling and logging
- 🐳 Docker-ready deployment with Docker Compose
- 🧪 Unit testing with coverage
- 🔨 Streamlined build and run with Makefile

---

## **🔧 Technology Stack**

- 🟩 **Java 17**
- 🟩 **Spring Boot 3.1.5**
- 🟩 **PostgreSQL**
- 🟩 **Docker & Docker Compose**
- 🟩 **Maven**
- 🟩 **JUnit 5**
- 🟩 **Lombok**
- 🟩 **MapStruct**

---

## **📂 Project Structure**

The project follows a layered architecture:

```plaintext
src/
├── main/
│   ├── java/com/bloomberg/fxdeals/
│   │   ├── controller/        # REST API controllers
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── exception/         # Custom exceptions
│   │   ├── mapper/            # DTO to Entity mappers
│   │   ├── model/             # JPA entities
│   │   ├── repository/        # Data access layer
│   │   ├── service/           # Business logic
│   │   └── FxDealsApplication.java  # Main class
│   └── resources/
│       └── application.properties   # Configuration
├── test/
│   └── java/com/bloomberg/fxdeals/
│       ├── service/           # Service tests
│       ├── controller/        # Controller tests
│       └── repository/        # Repository tests
```
# 🚀 Getting Started

## Prerequisites
- 🐳 **Docker and Docker Compose**
- ☕ **Java 17** (for local development)
- 🧱 **Maven** (or use the included Maven wrapper)

## 🔧 Setup & Installation

### **A. Clone the Repository**
```sh
git clone https://github.com/erradaoumaimaa/Bloomberg-FXDeals
cd Bloomberg-FXDeals
```
### **B. Run the Application**

#### **With Docker:**
```sh
make setup
```
## ✅ Validation Rules
The application enforces the following validation rules:

- ✅ **Deal ID** is required and must be unique
- ✅ **Currency codes** must be valid 3-letter ISO codes
- ✅ **Deal timestamp** must be in the past or present
- ✅ **Amount** must be positive

## 🧪 Testing

Run the tests with:
```sh
./mvnw test
```

# 🛠️ Makefile Commands

make build        # Build the application
make run          # Run the application locally
make test         # Run the tests
make docker-build # Build Docker image
make docker-run   # Run the application in Docker
make docker-stop  # Stop Docker containers
make setup        # Set up and start the application in Docker
make full-clean   # Stop Docker containers and clean artifacts

# 📞 Contact
# For any questions or suggestions, feel free to reach out:

# Name: Oumaima Errada
# Email: erradaoumaima@gmail.com
# GitHub: erradaOumaima

