# 🛒 E-Commerce Backend API
![ alt text ](https://img.shields.io/badge/Java-17%252B-orange?style=for-the-badge&logo=openjdk)
`<img src="https://img.shields.io/badge/Java-17%252B-orange?style=for-the-badge&logo=openjdk" />`
`<img src="https://img.shields.io/badge/Spring%2520Boot-3.x-green?style=for-the-badge&logo=spring" />`
`<img src="https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql" />`
`<img src="https://img.shields.io/badge/License-Proprietary-red?style=for-the-badge" />`

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
