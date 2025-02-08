# Bookstore Management System

## 📌 Project Overview
The **Bookstore Management System** is a full-stack web application that allows users to browse books, place orders, and manage their purchases. It features authentication, role-based access, and order management with secure JWT authentication.

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring Security, Hibernate, MySQL
- **Frontend:** React.js (if applicable)
- **Authentication:** JWT (JSON Web Token)
- **Database:** MySQL
- **Build Tool:** Maven
- **Version Control:** Git & GitHub

## 🚀 Features
✅ User authentication (Admin & Customer roles)  
✅ Secure JWT-based authentication  
✅ Browse books and manage orders  
✅ Admin dashboard for managing orders  
✅ RESTful API with Spring Boot  

---

## 📂 Project Structure
```
BookstoreManagement/
│── src/main/java/com/bookstore
│   ├── controller/        # REST Controllers
│   ├── entity/            # JPA Entity classes
│   ├── repository/        # Spring Data JPA Repositories
│   ├── service/           # Business Logic Services
│   ├── config/            # Security & App Configurations
│── src/main/resources/
│   ├── application.properties  # Database & App Configurations
│── pom.xml               # Maven Dependencies
│── README.md             # Project Documentation
```

---

## 🛠️ Installation & Setup
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/MadhavKumar10/Bookstore_Management_System.git
cd Bookstore_Management_System
```

### 2️⃣ Configure the Database
Modify `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

---

## 🔑 Authentication & Authorization
This system uses JWT authentication. The token must be passed in the `Authorization` header as `Bearer <token>` for secured endpoints.

### 🔹 Generate JWT Token (Login)
```http
POST /api/login
Content-Type: application/json
{
    "email": "user@example.com",
    "password": "password123"
}
```
**Response:**
```json
{
    "token": "your_jwt_token"
}
```

---

## 📌 API Endpoints

### 🔹 User Endpoints
| Method | Endpoint           | Description                |
|--------|-------------------|----------------------------|
| POST   | `/api/register`   | Register a new user       |
| POST   | `/api/login`      | User login (JWT token)    |

### 🔹 Order Endpoints
| Method | Endpoint            | Description                       |
|--------|--------------------|-----------------------------------|
| GET    | `/api/orders`       | Get orders of authenticated user |
| POST   | `/api/orders`       | Place a new order                |
| PUT    | `/api/orders/{id}/status` | Update order status (Admin) |

### 🔹 Admin Endpoints
| Method | Endpoint          | Description                 |
|--------|------------------|-----------------------------|
| GET    | `/api/admin/orders` | View all orders (Admin)    |

---

## 🛠️ Troubleshooting
**Issue:** `JWT Authentication failed: Unauthorized`
- Ensure the token is correctly included in the request headers.
- Check if the token has expired.

**Issue:** `Cannot connect to database`
- Ensure MySQL is running.
- Check `application.properties` database credentials.

---

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).

## 💡 Contributors
👤 **Madhav Kumar** - [GitHub](https://github.com/MadhavKumar10)

---

**Happy Coding! 🚀**

