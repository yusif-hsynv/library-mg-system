# 📚 Library Management System (Spring Boot)

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-blueviolet?logo=apachemaven)
![Lombok](https://img.shields.io/badge/Lombok-Automation-red?logo=lombok)

A simple **Library Management Application** built with **Spring Boot** and **MySQL**.  
It provides functionality for managing **Books**, **Authors**, and **Users**,  
featuring clean layered architecture and unified response handling.

---

## 🚀 Features
- CRUD operations for **Book**, **Author**, and **User**
- **Borrowing logic:** each book can be assigned to a user  
- **Many-to-Many** relationship between **Books** and **Authors**
- **Soft delete** support via `EnumAvailableStatus` (Active / Deactive)
- Centralized **Exception Handling** using `LibraryException` and `ExceptionConstants`
- Unified response format using `Response<T>` and `RespStatus`
- Clean layered architecture (Controller → Service → Repository)

---

## 🧠 Book Management Logic Overview
When creating or updating a book:
1. The system validates that all provided **author IDs** are active.  
2. It checks that the **book title** is valid and not null.  
3. It allows assigning a **User** (borrower) if applicable.  
4. The system maps entities to DTOs using builder patterns and returns responses in a unified format.  
5. Books and related entities are stored and retrieved from the database with active status filtering.

---

## ⚙️ Tech Stack
- Java 17  
- Spring Boot (Web, Data JPA)  
- MySQL  
- Maven  
- Lombok  

---

## 🧰 How to Run
```bash
git clone https://github.com/yusif-hsynv/library-management-spring.git
cd library-management-spring
mvn spring-boot:run
```
---
## 👨‍💻 Author  
**Yusif Hüseynov**  
*Java Developer | Spring Boot | REST APIs*  
[GitHub Profile](https://github.com/yusif-hsynv)
