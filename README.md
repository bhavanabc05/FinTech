# 💰 FinTech Backend API

A Spring Boot backend for managing users, transactions, budgets, goals, and financial insights.

---

# 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Spring Security

---

# 🌐 Base URL

http://localhost:8080

---

# ⚙️ Backend Setup

## 1. Clone & Run

git clone <repo-url>
cd fintech
mvn spring-boot:run

---

## 2. MySQL Setup

CREATE DATABASE FinTechDB;

Update `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/FinTechDB
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

---

# 🔐 AUTH APIs

## Register

POST /auth/register

{
"name": "John",
"email": "[john@example.com](mailto:john@example.com)",
"password": "123456"
}

---

## Login

POST /auth/login

{
"email": "[john@example.com](mailto:john@example.com)",
"password": "123456"
}

---

# 💰 TRANSACTION APIs

## Add Transaction

POST /transactions

{
"userId": 1,
"amount": 500,
"type": "expense",
"category": "Food",
"date": "2026-04-30"
}

---

## Get Transactions

GET /transactions/{userId}

---

## Summary

GET /transactions/summary/{userId}

---

## Category Summary

GET /transactions/category-summary/{userId}

---

## Delete Transaction

DELETE /transactions/{id}

---

# 🔍 FILTER APIs

GET /transactions/filter/type?userId=1&type=expense
GET /transactions/filter/category?userId=1&category=Food
GET /transactions/filter/date?userId=1&start=2026-04-01&end=2026-04-30

---

# 📊 BUDGET APIs

## Add Budget

POST /budgets

{
"userId": 1,
"category": "Food",
"limitAmount": 2000,
"month": 4,
"year": 2026
}

---

## Budget Status

GET /budgets/{userId}

---

## Delete Budget

DELETE /budgets/{id}

---

## Recommendations

GET /budgets/recommendations/{userId}

---

# 🎯 GOAL APIs

## Add Goal

POST /goals

{
"userId": 1,
"name": "Buy Laptop",
"targetAmount": 50000,
"savedAmount": 10000,
"deadline": "2026-12-01"
}

---

## Get Goals

GET /goals/{userId}

---

# 🧾 VALIDATION RULES

## User

* name → required
* email → valid format
* password → min 4 characters

## Transaction

* userId → required
* type → "income" or "expense"
* amount → positive (> 0)
* category → required
* date → YYYY-MM-DD

## Budget

* userId → required
* category → required
* limitAmount → positive
* month → 1–12
* year → valid

## Goal

* userId → required
* name → required
* targetAmount → > 0
* savedAmount → ≥ 0
* deadline → future date

---

# ⚙️ BUSINESS LOGIC & VALIDATION (IMPORTANT)

This backend includes additional runtime validation and intelligent responses beyond basic field validation.

---

# 💰 TRANSACTION RULES (STRICT)

## Type Validation

Transaction `type` must be:

income OR expense

Invalid example:
{
"type": "spend"
}

Response:
{
"success": false,
"message": "Invalid transaction type. Must be 'income' or 'expense'"
}

---

## Date Validation

* date cannot be null
* Must be valid YYYY-MM-DD

---

# ⚠️ BUDGET ALERT SYSTEM

Each transaction returns a dynamic alert:

* No budget → "No budget set"
* < 80% → "Within budget (X% used)"
* ≥ 80% → "⚠️ Warning: X% of budget used"
* ≥ 100% → "Budget exceeded! (X% used)"

---

# 📊 SUMMARY LOGIC (SMART INSIGHTS)

* No income → spending warning
* Negative savings → overspending
* Low savings → saving advice
* Good savings → positive feedback

---

# 🔍 FILTER BEHAVIOR

* type filter is case-insensitive
* date must be YYYY-MM-DD
* invalid date → error

---

# ❌ DELETE VALIDATION

Deleting non-existent transaction → error

---

# 🧠 FRONTEND NOTES

* "alert" → show after transaction
* "message" → show in dashboard
* both are meant for UI

---

# 🚨 EDGE CASES

* Savings can be negative
* Budget usage can exceed 100%
* No data → returns 0
* Filters can return empty list

---

# 🔁 RESPONSE FORMAT

Success:
{
"success": true,
"message": "Message",
"data": {...}
}

Error:
{
"success": false,
"message": "Error message",
"data": {...}
}

---

# ⚠️ IMPORTANT NOTES

* Backend must be running
* MySQL must be running
* Date format must be correct
* type must be lowercase

---

# 📦 FEATURES

* Authentication
* Transactions
* Budgets
* Goals
* Alerts
* Recommendations
* Filters
* Smart insights

---

# 🚀 FUTURE

* JWT Authentication
* Pagination
* Debt Management

---

# 👨‍💻 Maintainer

Backend Developers: b & j :3
