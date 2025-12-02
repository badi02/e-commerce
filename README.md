# 🛒 E-Commerce Backend API
![Build Status](https://img.shields.io/gitlab/pipeline-status/badi_ais/e-commerce?branch=main&style=for-the-badge&label=Build)
![Tests](https://img.shields.io/gitlab/pipeline-coverage/badi_ais/e-commerce?branch=main&style=for-the-badge&label=Tests)
![GitLab Issues](https://img.shields.io/gitlab/issues/open/badi_ais/e-commerce?style=for-the-badge&label=Issues)
![GitLab Merge Requests](https://img.shields.io/gitlab/mr-open/badi_ais/e-commerce?style=for-the-badge&label=MRs)
![Code Quality](https://img.shields.io/codefactor/grade/gitlab/badi_ais/e-commerce/main?style=for-the-badge&label=Code%20Quality)
![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-yellow?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-✓-blue?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Lombok](https://img.shields.io/badge/Lombok-✓-pink?style=for-the-badge&logo=lombok&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-✓-orange?style=for-the-badge)
![GitLab Last Commit](https://img.shields.io/gitlab/last-commit/badi_ais/e-commerce?style=for-the-badge&label=Last%20Commit)
![GitLab Repo Size](https://img.shields.io/gitlab/repo-size/badi_ais/e-commerce?style=for-the-badge&label=Repo%20Size)
![Lines of Code](https://img.shields.io/tokei/lines/gitlab/badi_ais/e-commerce?style=for-the-badge&label=Lines%20of%20Code)
![GitLab Contributors](https://img.shields.io/gitlab/contributors/badi_ais/e-commerce?style=for-the-badge&label=Contributors)
![GitLab Forks](https://img.shields.io/gitlab/forks/badi_ais/e-commerce?style=for-the-badge&label=Forks)
![Security Score](https://img.shields.io/security-headers?style=for-the-badge&url=https%3A%2F%2Fgitlab.com%2Fbadi_ais%2Fe-commerce)
![Dependencies](https://img.shields.io/librariesio/gitlab/badi_ais/e-commerce?style=for-the-badge&label=Dependencies)
![Response Time](https://img.shields.io/website?down_color=red&down_message=offline&style=for-the-badge&up_color=green&up_message=online&url=https%3A%2F%2Fgitlab.com%2Fbadi_ais%2Fe-commerce)
![Uptime](https://img.shields.io/uptimerobot/status/m790300024-40e025e7d8f3c4b8d6a1c0e7?style=for-the-badge&label=Uptime)
![API Status](https://img.shields.io/badge/API-✓%20RESTful-success?style=for-the-badge&logo=rest)
![Auth Type](https://img.shields.io/badge/Auth-JWT%20%2B%20BCrypt-blueviolet?style=for-the-badge&logo=key)
![Database](https://img.shields.io/badge/Database-PostgreSQL%20%2B%20JPA-336791?style=for-the-badge&logo=postgresql)
![Architecture](https://img.shields.io/badge/Architecture-Layered%20%2B%20Clean-blue?style=for-the-badge&logo=diagramsdotnet)

A fully functional **Spring Boot--based backend** for an e-commerce
platform.\
This application provides all essential features for managing products,
orders, categories, brands, towns (delivery areas), and admin
authentication.

It supports:

- Admin CRUD operations\
- Guest checkout\
- Product image uploads\
- JWT-secured admin operations\
- Stock management with optimistic locking\
- Caching for frequently accessed data\
- Swagger documentation\
- PostgreSQL persistence

---

## 🚀 Features

### 🔐 Authentication

- Admin login with JWT\
- Password change endpoint\
- Initial admin auto-created on startup\
- Role-based protection (ADMIN only)

### 🛍 Product Management

- Create, update, delete, and view products\
- Upload multiple product images\
- Search by name, category, price range\
- Pagination for product listing\
- Optimistic locking to prevent overselling

### 📦 Order Placement

- Guest-based order creation (no authentication required)
- Automatic stock deduction\
- Delivery fee calculation based on town\
- Order status management by admins

### 🗂 Category & Brand Management

- CRUD operations\
- Cached for performance

### 🗺 Town Management

- Delivery tax management per town\
- CRUD with admin access

### 🧪 Testing

- Unit tests (Mockito)\
- Integration tests\
- Repository tests

---

## 🧰 Technologies Used

Component Technology

---

**Language** Java 17+
**Framework** Spring Boot 3.x
**Security** Spring Security, JWT
**Database** PostgreSQL
**ORM** Spring Data JPA
**Mapping** MapStruct
**Documentation** Swagger/OpenAPI
**Utilities** Lombok, SLF4J
**Testing** JUnit, Mockito

---

## 📦 Prerequisites

- **Java JDK 17+**
- **Maven**
- **PostgreSQL**
- Optional: MailTrap for email (currently disabled)

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository

```bash
git clone https://gitlab.com/badi_ais/e-commerce
cd e-commerce
```

### 2️⃣ Configure Database

Create a PostgreSQL database:

    dbcom

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dbcom
spring.datasource.username=postgres
spring.datasource.password=your-password
```

### 3️⃣ Configure Admin Credentials

```properties
admin.email=admin@ecommerce.com
admin.password=Admin@Password@123
```

Admin is auto-created on first run.

### 4️⃣ Configure JWT & Uploads

- Customizable JWT secret\
- Images stored in `uploads/` directory

### 5️⃣ Build the Project

```bash
mvn clean install
```

### 6️⃣ Run the Application

```bash
mvn spring-boot:run
```

Access at:\
➡️ http://localhost:8080

### 7️⃣ Swagger Documentation

➡️ http://localhost:8080/swagger-ui.html

---

## 🗃 Database Schema (Key Entities)

### Users

- Admin accounts\
- Fields: id, email, password, role

### Products

- Name, description, price, quantity\
- List of image paths\
- category_id, brand_id\
- `@Version` field for optimistic locking

### Orders & Items

- Customer details\
- Delivery tax\
- Items list (productId, quantity, price)

### Towns

- Delivery zones & taxes

Indexes added for: - product name\

- product price\
- category_id

---

## 🔗 API Endpoints

### 🔐 Authentication

Method Endpoint Description

---

POST `/api/auth/login` Login & receive JWT
POST `/api/auth/change-password` Change admin password
GET `/api/auth/user/role` Get authenticated user role
GET `/api/auth/user/{id}` Get user info

---

### 🛍 Products

Method Endpoint Description

---

POST `/api/products` Create product (multipart)
PUT `/api/products/{id}` Update product
DELETE `/api/products/{id}` Delete product
GET `/api/products/{id}` Get product by ID
GET `/api/products` Paginated product list
GET `/api/products/search` Filter/search products

---

### 📦 Orders

Method Endpoint Description

---

POST `/api/orders` Place order (guest)
GET `/api/orders` Get all orders (admin)
GET `/api/orders/{id}` Get order by ID
PUT `/api/orders/{id}/status` Update order status
PUT `/api/orders/{id}/cancel` Cancel order

---

### 🗂 Categories & Brands

CRUD endpoints under:

- `/api/categories`\
- `/api/brands`

---

### 🗺 Towns

CRUD endpoints under:

- `/api/towns`

---

## 📈 Performance Optimizations

- Optimistic locking on product stock\
- DB indexing for search-heavy operations\
- Category/Brand caching with eviction\
- Planned switch to BigDecimal for precision

---

## 🧪 Testing

Run all tests:

```bash
mvn test
```

Includes: - Unit tests (Mockito)\

- Controller tests (@WebMvcTest)\
- Repository tests (@DataJpaTest)

---

## 🖼 Image Handling

- Images stored in `uploads/` directory\
- Removes old images on update/delete\
- Supports multiple image uploads per product

---

## 📝 Notes & Future Enhancements

- Optional email notifications (currently commented)\
- Customer accounts (planned)\
- Production recommendations:
  - HTTPS\
  - Environment variables for secrets\
  - Cloud image storage\
  - Rate limiting & CAPTCHA

---

## 👤 Contact

**Monder AISSOU**\
📧 monder.dev@outlook.com\
📞 +213675761221

---

## 🤝 Contributing

1.  Fork the repository\
2.  Create your feature branch\
3.  Commit your changes\
4.  Submit a Merge Request

---

## 📄 License

**MIT License**
