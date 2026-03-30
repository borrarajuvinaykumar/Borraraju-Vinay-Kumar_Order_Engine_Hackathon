# Borraraju-Vinay-Kumar_Order_Engine_Hackathon
# 🛒 Distributed Order Management System (Spring Boot)

A backend system simulating a real-world e-commerce order processing platform.  
Built using Spring Boot with focus on **concurrency, transactions, and system design principles**.

---

## 🚀 Features

### 🧾 Product & Inventory Management
- Add products with stock and pricing
- Update inventory
- Prevent negative stock

### 🛒 Cart System
- Multi-user cart support
- Add/remove items
- View cart

### 📦 Order Processing
- Convert cart → order
- Calculate total dynamically
- Unique order IDs

### 💳 Payment Simulation
- Simulated payment gateway
- Retry mechanism (3 attempts)
- Random failure handling

### 🔁 Transaction Rollback
- If payment fails → stock is restored
- Ensures atomic operations (All or Nothing)

### ⚡ Concurrency Handling
- Per-product locking using `ReentrantLock`
- Prevents overselling in multi-user scenarios

### 🔄 Failure Injection
- Random system failure simulation
- Ensures robustness under unexpected errors

### 🧠 Order State Machine
- Controlled order lifecycle:
