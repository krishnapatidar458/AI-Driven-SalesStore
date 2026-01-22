# 🛍️ AI-Driven SalesStore (Enterprise Microservices)

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-green?style=for-the-badge&logo=springboot)
![Spring AI](https://img.shields.io/badge/Spring_AI-0.8-blue?style=for-the-badge&logo=spring)
![Postgres](https://img.shields.io/badge/PostgreSQL-pgvector-336791?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)

> **A Next-Generation E-Commerce Platform demonstrating the future of Java development: Microservices, Event-Driven Architecture, and RAG-based AI integration.**

---

## 📖 Overview
**SalesStore** is a research project designed to explore how **Generative AI** integrates into a robust, enterprise-grade **Spring Boot Microservices** architecture.

Unlike generic tutorials, this project implements **"Production-First"** patterns:
* **RAG (Retrieval-Augmented Generation):** "Smart Search" that understands user intent (e.g., *"Show me sneakers for wide feet"*).
* **Event-Driven Consistency:** Using Kafka/RabbitMQ to sync data between the Transactional World (Orders) and the Semantic World (AI Vectors).
* **Polyglot Persistence:** Using Redis for carts, Postgres for orders, and pgvector for AI embeddings.

---

## 🏗️ Architecture

The system follows a **Distributed Microservices Architecture** where the AI Logic is decoupled from the core business logic to ensure scalability.

![System Architecture](docs/system-architecture.png)

### **Core Services**
| Service | Tech Stack | Responsibility |
| :--- | :--- | :--- |
| **Gateway** | Spring Cloud Gateway | Entry point, routing, and rate limiting. |
| **Auth Service** | Keycloak | Identity Provider (IdP), OIDC, Role-Based Access (Admin/Seller/User). |
| **Product Service** | Spring Boot, Postgres | Catalog management, Categories, and Variants (SKUs). |
| **Order Service** | Spring Boot, Postgres | Order lifecycle, Snapshot logic for historical pricing, Payment integration. |
| **AI Service** | **Spring AI**, pgvector | RAG implementation, Vector Embeddings, Chatbot Logic. |
| **Discovery** | Netflix Eureka | Service Registry. |

---

## 🚀 Key Features

### 🧠 AI & Intelligence
* **Semantic Search:** Finds products based on meaning, not just keyword matching.
* **Shopping Assistant:** A context-aware chatbot that answers questions about product specs using RAG.
* **Review Summarizer:** Automatically condenses hundreds of user reviews into "Pros & Cons."

### 🏢 Enterprise Patterns
* **Snapshot Pattern:** Order items store the `price_at_purchase` and `product_name_snapshot` to preserve historical accuracy even if the catalog changes.
* **Variant System:** Full support for Size/Color variants with unique SKUs and inventory tracking.
* **Resilience:** Implements **Resilience4j** circuit breakers to keep the store running even if the AI service is down.

---

## 🛠️ Technology Stack

* **Language:** Java 21 (LTS)
* **Framework:** Spring Boot 3.4+
* **AI Framework:** Spring AI (connecting to Ollama/OpenAI)
* **Databases:**
    * **PostgreSQL:** Relational Data (Products, Orders)
    * **pgvector:** Vector Embeddings
    * **Redis:** Caching & Session Management
* **Messaging:** Kafka / RabbitMQ
* **Security:** OAuth2 / OpenID Connect with Keycloak
* **DevOps:** Docker, Docker Compose, Zipkin (Tracing), Prometheus (Metrics)

---

## 🗄️ Database Design
The database follows the **Database-per-Service** pattern, ensuring strict isolation between microservices.

![Database Schema](docs/database-schema.png)

* **Product DB:** Manages Categories, Products, and Variants.
* **Order DB:** Manages Orders, OrderItems (Snapshots), and Payments.
* **Vector DB:** Stores Embedding Vectors linked logically to Product UUIDs.

---

## ⚡ Getting Started

### Prerequisites
* Java 21 JDK
* Docker Desktop (for running Postgres, Redis, Keycloak)
* Maven

### Installation
1.  **Clone the repository**
    ```bash
    git clone [https://github.com/krishnapatidar458/SalesStore-Backend.git](https://github.com/krishnapatidar458/SalesStore-Backend.git)
    cd SalesStore-Backend
    ```

2.  **Start Infrastructure (Database, Broker, Auth)**
    ```bash
    docker-compose up -d
    ```

3.  **Start Services**
    * Run `DiscoveryServiceApplication`
    * Run `ConfigServerApplication`
    * Run `ProductServiceApplication`
    * ... (others)

---

## 🗺️ Roadmap
- [x] **Phase 1: Requirement Analysis** (Defined Functional & Non-Functional Req)
- [x] **Phase 2: System Design** (Microservices Architecture, Tech Stack Selection)
- [x] **Phase 3: Database Modeling** (Schema Design with AI Vectors & Snapshots)
- [ ] **Phase 4: Backend Implementation** (Coding Services & APIs)
- [ ] **Phase 5: AI Integration** (RAG Implementation & Vector Ingestion)
- [ ] **Phase 6: Frontend Development** (React/Angular UI)

---

## 🤝 Contributing
This is a learning project. Suggestions and Pull Requests are welcome!

## 📜 License
MIT License
