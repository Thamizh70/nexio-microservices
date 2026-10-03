<div align="center">

<img src="docs/images/nexio-banner.png" alt="Nexio Ride-Hailing Microservices Platform" width="100%"/>

</div>

---

## ✨ Overview

**Nexio** is a ride-hailing platform built with **Java 21 and Spring Boot**, organized as a set of independently deployable microservices.

The current project models the rider and driver journey:

> 📱 Account creation → 🚖 Ride booking → 🧭 Driver assignment → 📍 Live tracking → 💳 Payment → ⭐ Rating → 🕘 Ride history

> [!NOTE]
> **Project status: 🚧 Active development.** The repository contains service foundations and is being implemented incrementally. Some integrations and distributed-system features listed in the roadmap are planned rather than fully implemented.

---

## 📱 Product Flow

<div align="center">

![Nexio mobile application flow](docs/images/nexio-mobile-ui-flow.png)

</div>

```mermaid
flowchart LR
    A([🚀 Splash]) --> B[📞 Mobile Number]
    B --> C[🔐 OTP Verification]
    C --> D[👤 Profile Creation]
    D --> E[📍 Location Permission]
    E --> F[🏦 Payout Setup]
    F --> G([✅ Account Created])

    G --> H[🏠 Home / Book a Ride]
    H --> I[🗺️ Destination]
    I --> J[💰 Fare & Vehicle]
    J --> K[🔎 Driver Search]
    K --> L[🧑‍✈️ Driver Assigned]
    L --> M[📡 Live Tracking]
    M --> N[🚗 Driver Arrival]
    N --> O[🛣️ Ride in Progress]
    O --> P[🏁 Destination Reached]
    P --> Q[💳 Payment]
    Q --> R[🎉 Payment Success]
    R --> S[⭐ Rating]
    S --> T[🕘 Ride History]

    classDef onboarding fill:#111111,stroke:#FFD21F,color:#FFD21F,stroke-width:2px
    classDef booking fill:#1A1A1A,stroke:#FFD21F,color:#FFFFFF,stroke-width:2px
    classDef trip fill:#FFD21F,stroke:#A87900,color:#111111,stroke-width:2px
    classDef finish fill:#2A2A2A,stroke:#FFD21F,color:#FFFFFF,stroke-width:2px

    class A,B,C,D,E,F,G onboarding
    class H,I,J,K,L booking
    class M,N,O,P trip
    class Q,R,S,T finish
```

| ⚫ Onboarding | 🟡 Booking | 🟨 Trip | ⚪ Completion |
|:--|:--|:--|:--|
| Splash / Welcome | Home / Book a Ride | Live trip tracking | Payment |
| Mobile number entry | Destination selection | Driver arrival | Payment success |
| OTP verification | Fare & vehicle selection | Ride in progress | Ride rating |
| User profile creation | Driver search | Destination reached | Ride history |
| Location permission | Driver assignment | | Profile / Wallet / Settings |
| Bank account / payout setup | | | |

---

## 🧩 Microservices

| | Service | Responsibility |
|:-:|:--|:--|
| 🚪 | `nexio-api-gateway` | Single entry point and request routing |
| 🧭 | `nexio-service-discovery` | Service registration and discovery |
| 🔐 | `nexio-auth-service` | Authentication, OTP, JWT, refresh tokens, and account security |
| 👤 | `nexio-user-service` | User profile and user-related operations |
| 🧑‍✈️ | `nexio-driver-service` | Driver registration and driver management |
| 🚖 | `nexio-ride-service` | Ride booking, matching, trip lifecycle, and ride state |
| 📍 | `nexio-location-service` | Real-time location and trip tracking |
| 💰 | `nexio-pricing-service` | Fare calculation and pricing rules |
| 💳 | `nexio-payment-service` | Payment and transaction processing |
| 🔔 | `nexio-notification-service` | Notifications such as SMS, email, and push |
| ⭐ | `nexio-rating-service` | Rider and driver ratings / reviews |

---

## 🏗️ Architecture

```mermaid
flowchart TB
    Client(["📱 Mobile / Web Client"])

    Gateway{{"🚪 API Gateway"}}
    Discovery{{"🧭 Service Discovery"}}

    Auth["🔐 Auth Service"]
    User["👤 User Service"]
    Driver["🧑‍✈️ Driver Service"]
    Ride["🚖 Ride Service"]
    Location["📍 Location Service"]
    Pricing["💰 Pricing Service"]
    Payment["💳 Payment Service"]
    Notification["🔔 Notification Service"]
    Rating["⭐ Rating Service"]

    Client --> Gateway
    Gateway --> Discovery

    Gateway --> Auth
    Gateway --> User
    Gateway --> Driver
    Gateway --> Ride
    Gateway --> Location
    Gateway --> Pricing
    Gateway --> Payment
    Gateway --> Notification
    Gateway --> Rating

    Discovery -. registers .-> Auth
    Discovery -. registers .-> User
    Discovery -. registers .-> Driver
    Discovery -. registers .-> Ride
    Discovery -. registers .-> Location
    Discovery -. registers .-> Pricing
    Discovery -. registers .-> Payment
    Discovery -. registers .-> Notification
    Discovery -. registers .-> Rating

    Ride --> Location
    Ride --> Pricing
    Ride --> Payment
    Ride --> Notification
    Ride --> Rating

    classDef client fill:#0B0B0B,stroke:#FFD21F,color:#FFFFFF,stroke-width:2px
    classDef infra fill:#111111,stroke:#FFD21F,color:#FFD21F,stroke-width:2px
    classDef core fill:#FFD21F,stroke:#A87900,color:#111111,stroke-width:3px
    classDef svc fill:#1A1A1A,stroke:#FFD21F,color:#FFFFFF,stroke-width:2px
    classDef money fill:#2A2A2A,stroke:#FFD21F,color:#FFFFFF,stroke-width:2px

    class Client client
    class Gateway,Discovery infra
    class Ride core
    class Auth,User,Driver,Location,Notification,Rating svc
    class Pricing,Payment money
```

---

## 🛠️ Technology Stack

### ☕ Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA / Hibernate
- REST APIs
- Maven
- PostgreSQL

### ☁️ Distributed Systems & Infrastructure

- Spring Cloud
- Service Discovery
- API Gateway
- Kafka *(planned / being integrated)*
- Redis *(planned / being integrated)*
- WebSocket *(planned / being integrated)*
- Docker *(planned / being integrated)*
- Kubernetes *(planned)*

### 🧪 API Testing

- Postman

--- 

## 📂 Repository Structure

```text
nexio-microservices/
│
├── 🚪 nexio-api-gateway/
├── 🔐 nexio-auth-service/
├── 🧑‍✈️ nexio-driver-service/
├── 📍 nexio-location-service/
├── 🔔 nexio-notification-service/
├── 💳 nexio-payment-service/
├── 💰 nexio-pricing-service/
├── ⭐ nexio-rating-service/
├── 🚖 nexio-ride-service/
├── 🧭 nexio-service-discovery/
├── 👤 nexio-user-service/
│
├── 📬 postman/
├── 📚 docs/
├── 🙈 .gitignore
└── 📄 README.md
```

---

## 🚀 Getting Started

### 📋 Prerequisites

| Required | Optional (as the project evolves) |
|:--|:--|
| ☕ JDK 21 | 🔴 Redis |
| 📦 Maven Wrapper *(included in each service)* | 📨 Kafka |
| 🐘 PostgreSQL | 🐳 Docker Desktop |
| 🌱 Git | |
| 📬 Postman | |

### ▶️ Run a service

From the required service directory:

```powershell
cd nexio-auth-service
.\mvnw.cmd spring-boot:run
```

Repeat for other services as required by your local environment.

> [!TIP]
> Start `nexio-service-discovery` first, then `nexio-api-gateway`, followed by the services you want to work with.

### 🔨 Build a service

```powershell
.\mvnw.cmd clean package
```

---

## 📬 Postman

Exported Postman workspace data lives under:

```text
postman/
```

> [!WARNING]
> Use environment variables for local credentials and secrets. **Never commit** real passwords, JWT signing secrets, payment-provider secrets, API keys, or OTP-provider credentials.

---

## 🌿 Git Workflow

```mermaid
gitGraph
    commit id: "init"
    branch develop
    checkout develop
    commit id: "setup"
    branch feature/auth
    commit id: "auth"
    checkout develop
    merge feature/auth
    branch feature/ride
    commit id: "ride"
    checkout develop
    merge feature/ride
    checkout main
    merge develop tag: "v0.1.0"
```

```powershell
git checkout -b feature/ride

git add .
git commit -m "Implement ride booking flow"

git push -u origin feature/ride
```

Merge completed features into `develop`; merge reviewed and tested changes into `main`.

---

## 🧠 Development Principles

- 🎯 Keep each service focused on a clear business responsibility.
- 🗄️ Avoid sharing database tables directly between services.
- 🔒 Keep secrets outside source control.
- 📜 Use API contracts for service-to-service communication.
- ⚡ Prefer asynchronous events for workflows that do not require synchronous responses.
- 🧪 Add automated tests as business logic grows.
- 🐳 Containerize services only after their local configuration is stable.

---

## 🗺️ Roadmap

### Foundation

- [x] Initial microservices repository
- [x] API Gateway service foundation
- [x] Service Discovery service foundation
- [x] Authentication service foundation
- [x] User service foundation
- [x] Driver service foundation
- [x] Ride service foundation
- [x] Location service foundation
- [x] Pricing service foundation
- [x] Payment service foundation
- [x] Notification service foundation
- [x] Rating service foundation

### 🚧 Up next

- [ ] Complete service-to-service communication
- [ ] Complete PostgreSQL persistence
- [ ] Kafka event flows
- [ ] Redis caching
- [ ] Real-time WebSocket tracking
- [ ] Payment-provider integration
- [ ] Docker Compose environment
- [ ] Kubernetes deployment
- [ ] CI/CD pipeline
- [ ] Integration and end-to-end tests

--- 

## 📄 License

No license has been selected yet. Until a license is added, others should not assume they have permission to reuse or redistribute this code.

---

<div align="center">

**Built with ☕ and 💛 using Java 21 & Spring Boot**


<img src="https://capsule-render.vercel.app/api?type=waving&color=0B0B0B&height=120&section=footer&color2=FFD21F" alt="footer" width="100%"/>

</div>
