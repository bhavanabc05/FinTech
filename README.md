# 💰 FinTech Backend API

A Spring Boot backend for managing users, transactions, budgets, and financial insights.

---

# 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Spring Security

---

# 🌐 Base URL

```
http://localhost:8080
```

---

# ⚙️ Backend Setup

## 1. Clone & Run

```
git clone <repo-url>
cd fintech
```

Run in IntelliJ or:

```
mvn spring-boot:run
```

---

## 2. MySQL Setup

### Create Database

```sql
CREATE DATABASE FinTechDB;
```

---

### Update `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/FinTechDB
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 3. Run Backend

App will run on:

```
http://localhost:8080
```

---

# 🔐 Authentication APIs

## Register

POST `/auth/register`

```
{
  "name": "John",
  "email": "john@example.com",
  "password": "123456"
}
```

---

## Login

POST `/auth/login`

```
{
  "email": "john@example.com",
  "password": "123456"
}
```

---

# 💰 Transaction APIs

## Add Transaction

POST `/transactions`

```
{
  "userId": 1,
  "amount": 500,
  "type": "expense",
  "category": "Food",
  "date": "2026-04-30"
}
```

---

## Get All Transactions

GET `/transactions/user/{userId}`

---

## Summary

GET `/transactions/summary/{userId}`

Response:

```
{
  "totalIncome": 10000,
  "totalExpense": 3500,
  "savings": 6500,
  "message": "Good job! You are saving money."
}
```

---

## Category Summary

GET `/transactions/category-summary/{userId}`

---

## Delete Transaction

DELETE `/transactions/{id}`

---

# 🔍 Filters

## By Type

GET `/transactions/filter/type?userId=1&type=expense`

## By Category

GET `/transactions/filter/category?userId=1&category=Food`

## By Date

GET `/transactions/filter/date?userId=1&start=2026-04-01&end=2026-04-30`

---

# 📊 Budget APIs

## Add Budget

POST `/budgets`

```
{
  "userId": 1,
  "category": "Food",
  "limitAmount": 2000,
  "month": 4,
  "year": 2026
}
```

---

## Budget Status

GET `/budgets/{userId}`

---

## Delete Budget

DELETE `/budgets/{id}`

---

# 🧠 Smart Features

## Recommendations

GET `/budgets/recommendations/{userId}`

---

# 🔌 Frontend Integration Notes

## Headers

No authentication token yet (basic setup).
Future upgrade: JWT.

---

## Data Types

* `type` → `"income"` or `"expense"`
* `date` → `"YYYY-MM-DD"`
* `userId` → integer

---

## Common Response Format

```
{
  "success": true,
  "message": "Some message",
  "data": { ... }
}
```

---

# ⚠️ Important Notes

* Backend must be running before frontend
* MySQL must be running
* Ensure correct DB credentials
* Restart backend after config changes

---

# 📦 Features Implemented

* User Authentication
* Transaction Management
* Budget Tracking
* Spending Analytics
* Smart Recommendations
* Filtering APIs

---

# 🚀 Future Enhancements

* JWT Authentication
* Pagination
* Notifications

