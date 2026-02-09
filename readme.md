# 🛍️ E-Commerce Application with AI & Event-Driven Architecture

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Latest-black?style=for-the-badge&logo=apache-kafka)
![Docker](https://img.shields.io/badge/Docker-Latest-blue?style=for-the-badge&logo=docker)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-purple?style=for-the-badge)
![AI](https://img.shields.io/badge/AI-RAG%20Chatbot-red?style=for-the-badge&logo=openai)

**A Modern, AI-Powered E-Commerce Platform Built with Enterprise-Grade Technologies**

[Features](#-features) • [Architecture](#-architecture) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started) • [API Documentation](#-api-documentation) • [Contributing](#-contributing)

![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_6.png](img_6.png)
![chat](chat.jpg)
![image](image.png)
![alt text](img_8.png)  
![alt text](img_9.png)  
![alt text](img_10.png)  
![alt text](img_11.png)  



</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [Microservices](#-microservices)
- [AI Chatbot with RAG](#-ai-chatbot-with-rag)
- [Event-Driven Architecture](#-event-driven-architecture-with-kafka)
- [Database Schema](#-database-schema)
- [Testing](#-testing)
- [Monitoring & Observability](#-monitoring--observability)
- [Deployment](#-deployment)
- [Roadmap](#-roadmap)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## 🌟 Overview

**ecom-ii-bdcc-app** is a cutting-edge, full-stack e-commerce platform that combines modern microservices architecture with artificial intelligence capabilities. Built using Spring Boot and leveraging Apache Kafka for event-driven communication, this application demonstrates enterprise-level design patterns and best practices.

### What Makes This Project Special?

✨ **AI-Powered Customer Support** - Intelligent chatbot using Retrieval-Augmented Generation (RAG)  
⚡ **Event-Driven Architecture** - Real-time processing with Apache Kafka  
🏗️ **Microservices Design** - Scalable, maintainable, and resilient architecture  
🔐 **Enterprise Security** - OAuth2, JWT, and Spring Security integration  
📊 **Full Observability** - Distributed tracing, metrics, and logging  
🐳 **Cloud-Ready** - Containerized with Docker and Kubernetes support  

---

## ✨ Features

### 🛒 Core E-Commerce Functionality
- **Product Catalog Management** - Browse, search, and filter products
- **Shopping Cart** - Add, update, and remove items with real-time updates
- **Order Processing** - Complete checkout with order tracking
- **Payment Integration** - Secure payment processing
- **Inventory Management** - Real-time stock tracking and updates
- **User Management** - Authentication, authorization, and user profiles
- **Order History** - Track past orders and download invoices

### 🤖 AI-Powered Features
- **Intelligent Chatbot** - Customer support with RAG (Retrieval-Augmented Generation)
- **Product Recommendations** - AI-driven personalized suggestions
- **Natural Language Search** - Search products using conversational queries
- **Automated Customer Support** - 24/7 assistance with context-aware responses

### 📡 Real-Time Capabilities
- **Event-Driven Processing** - Asynchronous order processing via Kafka
- **Real-Time Notifications** - Order updates, inventory alerts, and promotions
- **Live Inventory Updates** - Stock synchronization across services
- **Analytics Events** - User behavior tracking and analytics

---

## 🏗️ Architecture

This application follows a **microservices architecture** pattern with event-driven communication:

```
┌─────────────────────────────────────────────────────────────────┐
│                         API Gateway                              │
│                    (Spring Cloud Gateway)                        │
└────────────┬────────────────────────────────────────────────────┘
             │
    ┌────────┴────────┐
    │                 │
    ▼                 ▼
┌─────────┐      ┌──────────┐       ┌──────────────┐
│ Product │      │  Order   │       │  Inventory   │
│ Service │      │ Service  │       │   Service    │
└────┬────┘      └────┬─────┘       └──────┬───────┘
     │                │                     │
     │           ┌────┴─────────────────────┘
     │           │
     └───────────┼─────────────────────────────────────┐
                 ▼                                     ▼
         ┌───────────────┐                    ┌───────────────┐
         │ Apache Kafka  │                    │   AI Chatbot  │
         │  Message Bus  │                    │  RAG Service  │
         └───────┬───────┘                    └───────────────┘
                 │
    ┌────────────┼────────────┐
    ▼            ▼            ▼
┌─────────┐ ┌─────────┐ ┌──────────┐
│ Payment │ │Shipping │ │Analytics │
│ Service │ │ Service │ │ Service  │
└─────────┘ └─────────┘ └──────────┘
```

### Architecture Highlights

- **Service Discovery** - Eureka for dynamic service registration
- **Configuration Management** - Centralized config with Spring Cloud Config
- **Load Balancing** - Client-side load balancing with Spring Cloud LoadBalancer
- **Circuit Breaker** - Resilience4j for fault tolerance
- **API Gateway** - Single entry point with routing and filtering
- **Distributed Tracing** - Sleuth + Zipkin for request tracing
- **Event-Driven** - Kafka for asynchronous microservice communication

---

## 🛠️ Tech Stack

### Backend
- **Java 17+** - Modern Java features and performance
- **Spring Boot 3.x** - Application framework
- **Spring Cloud** - Microservices infrastructure
- **Spring Data JPA** - Data persistence
- **Spring Security** - Authentication & authorization
- **Spring AI** - AI integration framework

### AI & Machine Learning
- **OpenAI API / LangChain4j** - LLM integration
- **Vector Database (PGVector/Pinecone)** - Embeddings storage
- **RAG (Retrieval-Augmented Generation)** - Context-aware AI responses

### Message Broker & Streaming
- **Apache Kafka** - Event streaming platform
- **Zookeeper** - Kafka cluster coordination

### Databases
- **PostgreSQL** - Primary relational database
- **MongoDB** - Product catalog and document storage
- **Redis** - Caching and session management

### DevOps & Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Kubernetes** - Container orchestration (optional)
- **Maven** - Build automation

### Monitoring & Observability
- **Spring Boot Actuator** - Health checks and metrics
- **Prometheus** - Metrics collection
- **Grafana** - Metrics visualization
- **Zipkin** - Distributed tracing
- **ELK Stack** - Centralized logging (optional)

### Testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **TestContainers** - Integration testing with containers
- **RestAssured** - API testing

---

## 📁 Project Structure

```
ecom-ii-bdcc-app/
├── api-gateway/                 # API Gateway service
├── service-discovery/           # Eureka service registry
├── config-server/               # Centralized configuration
├── product-service/             # Product catalog management
├── order-service/               # Order processing
├── inventory-service/           # Stock management
├── user-service/                # User authentication & profiles
├── payment-service/             # Payment processing
├── shipping-service/            # Shipping and delivery
├── chatbot-service/             # AI Chatbot with RAG
├── notification-service/        # Email & SMS notifications
├── analytics-service/           # Business analytics
├── kafka-config/                # Kafka configuration
├── common-lib/                  # Shared utilities and DTOs
├── docker/                      # Docker configurations
│   ├── docker-compose.yml
│   └── Dockerfile
├── k8s/                         # Kubernetes manifests
├── docs/                        # Documentation
│   ├── api/
│   ├── architecture/
│   └── guides/
├── scripts/                     # Utility scripts
├── .env.example                 # Environment variables template
├── pom.xml                      # Parent POM
└── README.md
```

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17+**
  ```bash
  java -version
  ```

- **Maven 3.8+**
  ```bash
  mvn -version
  ```

- **Docker & Docker Compose**
  ```bash
  docker --version
  docker-compose --version
  ```

- **Git**
  ```bash
  git --version
  ```

### Optional (for production deployment)
- Kubernetes (kubectl, minikube, or cloud provider)
- PostgreSQL 14+ (if not using Docker)
- MongoDB 6+ (if not using Docker)
- Redis 7+ (if not using Docker)

---

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Oussamoux1234/ecom-ii-bdcc-app.git
cd ecom-ii-bdcc-app
```

### 2. Set Up Environment Variables

Create a `.env` file from the template:

```bash
cp .env.example .env
```

Edit `.env` and configure your settings:

```env
# Database Configuration
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=ecommerce
POSTGRES_USER=admin
POSTGRES_PASSWORD=your_secure_password

MONGO_HOST=localhost
MONGO_PORT=27017
MONGO_DB=products

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
ZOOKEEPER_HOST=localhost:2181

# AI Configuration (OpenAI/LangChain)
OPENAI_API_KEY=your_openai_api_key
VECTOR_DB_URL=your_vector_database_url

# Security
JWT_SECRET=your_jwt_secret_key_min_256_bits
JWT_EXPIRATION=86400000

# Service Ports
API_GATEWAY_PORT=8080
EUREKA_PORT=8761
CONFIG_SERVER_PORT=8888
PRODUCT_SERVICE_PORT=8081
ORDER_SERVICE_PORT=8082
CHATBOT_SERVICE_PORT=8085
```

### 3. Build the Project

Build all microservices:

```bash
./mvnw clean install
```

Or build individual services:

```bash
cd product-service
./mvnw clean install
```

---

## ⚙️ Configuration

### Spring Cloud Config

All microservices fetch their configuration from the **config-server**. Configuration files are stored in:

```
config-server/src/main/resources/config/
├── application.yml           # Common configuration
├── product-service.yml       # Product service config
├── order-service.yml         # Order service config
├── chatbot-service.yml       # Chatbot service config
└── ...
```

### Kafka Topics

The application uses the following Kafka topics:

| Topic | Producer | Consumer | Purpose |
|-------|----------|----------|---------|
| `order-created` | order-service | inventory, payment, notification | New order processing |
| `order-confirmed` | payment-service | shipping, notification | Order confirmation |
| `inventory-updated` | inventory-service | product-service | Stock updates |
| `payment-processed` | payment-service | order-service | Payment confirmation |
| `chatbot-queries` | chatbot-service | analytics-service | Chatbot interactions |

---

## 🎯 Running the Application

### Option 1: Docker Compose (Recommended)

Start all services with Docker Compose:

```bash
docker-compose up -d
```

This will start:
- All microservices
- PostgreSQL
- MongoDB
- Redis
- Kafka + Zookeeper
- Zipkin
- Prometheus & Grafana

Check running containers:

```bash
docker-compose ps
```

View logs:

```bash
docker-compose logs -f [service-name]
```

Stop all services:

```bash
docker-compose down
```

### Option 2: Run Services Individually

1. **Start Infrastructure Services** (PostgreSQL, MongoDB, Redis, Kafka)

```bash
docker-compose up -d postgres mongodb redis kafka zookeeper
```

2. **Start Spring Boot Services** in order:

```bash
# Start Config Server
cd config-server && ./mvnw spring-boot:run

# Start Service Discovery
cd service-discovery && ./mvnw spring-boot:run

# Start API Gateway
cd api-gateway && ./mvnw spring-boot:run

# Start Business Services
cd product-service && ./mvnw spring-boot:run
cd order-service && ./mvnw spring-boot:run
cd inventory-service && ./mvnw spring-boot:run
cd chatbot-service && ./mvnw spring-boot:run
# ... etc
```

### Option 3: Using Scripts

```bash
# Start all services
./scripts/start-all.sh

# Stop all services
./scripts/stop-all.sh

# Restart specific service
./scripts/restart-service.sh product-service
```

---

## 📚 API Documentation

### Access Swagger UI

Once the application is running, access API documentation at:

```
http://localhost:8080/swagger-ui.html
```

### Key Endpoints

#### Product Service
```
GET    /api/products              - Get all products
GET    /api/products/{id}         - Get product by ID
POST   /api/products              - Create new product
PUT    /api/products/{id}         - Update product
DELETE /api/products/{id}         - Delete product
GET    /api/products/search       - Search products
```

#### Order Service
```
GET    /api/orders                - Get user orders
GET    /api/orders/{id}           - Get order details
POST   /api/orders                - Create new order
PUT    /api/orders/{id}/status    - Update order status
POST   /api/orders/{id}/cancel    - Cancel order
```

#### Chatbot Service
```
POST   /api/chatbot/query         - Send message to chatbot
GET    /api/chatbot/history       - Get chat history
POST   /api/chatbot/feedback      - Submit feedback
```

#### User Service
```
POST   /api/auth/register         - Register new user
POST   /api/auth/login            - User login
POST   /api/auth/refresh          - Refresh JWT token
GET    /api/users/profile         - Get user profile
PUT    /api/users/profile         - Update profile
```

### Sample API Requests

#### Create Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "items": [
      {
        "productId": "123",
        "quantity": 2
      }
    ],
    "shippingAddress": {
      "street": "123 Main St",
      "city": "New York",
      "zipCode": "10001"
    }
  }'
```

#### Query Chatbot
```bash
curl -X POST http://localhost:8080/api/chatbot/query \
  -H "Content-Type: application/json" \
  -d '{
    "message": "What are your best-selling products?",
    "userId": "user123"
  }'
```

---

## 🤖 AI Chatbot with RAG

### How RAG Works in This Application

**RAG (Retrieval-Augmented Generation)** enhances the chatbot's responses by retrieving relevant context from your knowledge base before generating answers.

#### Architecture

```
User Query
    │
    ▼
┌─────────────────┐
│  Chatbot API    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐      ┌──────────────────┐
│  Query Embedding│─────▶│  Vector Database │
│   (OpenAI)      │      │   (PGVector)     │
└─────────────────┘      └────────┬─────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │ Relevant Context │
                         │   Retrieval      │
                         └────────┬─────────┘
                                  │
                                  ▼
┌──────────────────────────────────────────┐
│  LLM (GPT-4) with Context                │
│  "Generate response using this context..." │
└────────────────┬─────────────────────────┘
                 │
                 ▼
           Final Response
```

### Features

- **Context-Aware Responses** - Uses your product catalog, FAQs, and documentation
- **Multi-turn Conversations** - Maintains conversation history
- **Intent Recognition** - Understands user needs (support, product info, orders)
- **Fallback Handling** - Graceful degradation when answers aren't available
- **Analytics** - Track common queries and improve knowledge base

### Configuration

Configure in `chatbot-service/src/main/resources/application.yml`:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4
          temperature: 0.7
    vectorstore:
      pgvector:
        url: ${VECTOR_DB_URL}
        dimensions: 1536
```

---

## 📡 Event-Driven Architecture with Kafka

### Why Kafka?

- **Asynchronous Processing** - Decouple services for better scalability
- **Event Sourcing** - Maintain complete audit trail of all events
- **Real-time Analytics** - Process events for business insights
- **Resilience** - Services can be offline without losing events

### Event Flow Example: Order Creation

```
1. User creates order
   │
   ▼
2. Order Service saves order & publishes "order-created" event
   │
   ▼
   ├──▶ Inventory Service (reserves stock)
   ├──▶ Payment Service (processes payment)
   └──▶ Notification Service (sends confirmation email)
   
3. Payment Service publishes "payment-processed" event
   │
   ▼
   ├──▶ Shipping Service (creates shipment)
   └──▶ Order Service (updates order status)

4. Notification Service sends updates at each step
```

### Kafka Configuration

Producer configuration example:

```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      properties:
        spring.json.type.mapping: orderEvent:com.ecommerce.order.OrderEvent
```

---

## 🗄️ Database Schema

### PostgreSQL (Relational Data)

**Users Table**
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Orders Table**
```sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    status VARCHAR(20),
    total_amount DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### MongoDB (Product Catalog)

**Product Document**
```json
{
  "_id": "product123",
  "name": "Wireless Headphones",
  "description": "High-quality Bluetooth headphones",
  "price": 99.99,
  "category": "Electronics",
  "tags": ["audio", "wireless", "bluetooth"],
  "inventory": {
    "stock": 150,
    "warehouse": "WH-001"
  },
  "embeddings": [0.123, 0.456, ...],
  "createdAt": "2025-01-15T10:30:00Z"
}
```

---

## 🧪 Testing

### Run All Tests

```bash
./mvnw test
```

### Run Integration Tests

```bash
./mvnw verify
```

### Run Tests for Specific Service

```bash
cd product-service
./mvnw test
```

### Test Coverage

Generate coverage report:

```bash
./mvnw jacoco:report
```

View report at: `target/site/jacoco/index.html`

### Testing with Postman

Import the Postman collection:

```
docs/api/ecommerce-api-collection.json
```

---

## 📊 Monitoring & Observability

### Access Monitoring Dashboards

| Service | URL | Purpose |
|---------|-----|---------|
| Eureka Dashboard | http://localhost:8761 | Service discovery |
| Swagger UI | http://localhost:8080/swagger-ui.html | API documentation |
| Zipkin | http://localhost:9411 | Distributed tracing |
| Prometheus | http://localhost:9090 | Metrics collection |
| Grafana | http://localhost:3000 | Metrics visualization |
| Kafka UI | http://localhost:8090 | Kafka monitoring |

### Default Credentials

- **Grafana**: admin / admin
- **Kafka UI**: No authentication

### Key Metrics to Monitor

- **Request Latency** - Response times across services
- **Error Rates** - Failed requests and exceptions
- **Kafka Lag** - Message processing delays
- **JVM Metrics** - Memory, GC, thread pools
- **Database Connections** - Connection pool usage

---

## 🚢 Deployment

### Docker Deployment

Build and push images:

```bash
./scripts/build-docker-images.sh
docker push your-registry/product-service:latest
docker push your-registry/order-service:latest
# ... repeat for all services
```

### Kubernetes Deployment

Apply Kubernetes manifests:

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmaps/
kubectl apply -f k8s/secrets/
kubectl apply -f k8s/deployments/
kubectl apply -f k8s/services/
kubectl apply -f k8s/ingress/
```

Check deployment status:

```bash
kubectl get pods -n ecommerce
kubectl get services -n ecommerce
```

### Production Considerations

- [ ] Use managed databases (RDS, Cloud SQL)
- [ ] Set up managed Kafka (MSK, Confluent Cloud)
- [ ] Configure auto-scaling
- [ ] Set up SSL/TLS certificates
- [ ] Implement rate limiting
- [ ] Configure backup and disaster recovery
- [ ] Set up monitoring alerts
- [ ] Implement log aggregation (ELK stack)

---

## 🗺️ Roadmap

### Phase 1: Core Features (Completed ✅)
- [x] Microservices architecture setup
- [x] Product catalog management
- [x] Order processing
- [x] User authentication
- [x] Kafka event streaming

### Phase 2: AI Integration (Completed ✅)
- [x] RAG chatbot implementation
- [x] Vector database integration
- [x] Natural language search

### Phase 3: Enhanced Features (In Progress 🚧)
- [ ] Advanced product recommendations
- [ ] Real-time inventory sync
- [ ] Multi-language support
- [ ] Mobile app integration
- [ ] Advanced analytics dashboard

### Phase 4: Enterprise Features (Planned 📋)
- [ ] Multi-tenant support
- [ ] Advanced fraud detection
- [ ] Blockchain for order tracking
- [ ] AR product visualization
- [ ] Voice commerce integration

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Code Style

- Follow Java coding conventions
- Write unit tests for new features
- Update documentation as needed
- Use meaningful commit messages

### Reporting Issues

Please include:
- Description of the issue
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)
- Environment details

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 Contact

**Oussama Essalmani**

- GitHub: [@Oussamoux1234](https://github.com/Oussamoux1234)
- Email: your.email@example.com
- LinkedIn: [Your LinkedIn Profile](https://linkedin.com/in/yourprofile)

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Apache Kafka community
- OpenAI for GPT-4 API
- All contributors who have helped with this project

---

<div align="center">

**⭐ If you find this project useful, please consider giving it a star! ⭐**

Made with ❤️ by [Oussama Essalmani](https://github.com/Oussamoux1234)

</div>

