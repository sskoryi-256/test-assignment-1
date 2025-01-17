# **README**

This project provides a **Video Streaming API** built with **Spring Boot** (Java 17+). It supports publishing and listing videos, tracking impressions/views (counters), and database migrations via **Flyway**.

---

## **1. How to Compile and Run**

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sskoryi-256/test-assignment-1.git
   cd test-assignment-1
   ```
2. **Build (Maven):**
   ```bash
   mvn clean package
   ```
   This creates a **fat JAR** in `target/`.
3. **Run:**
   ```bash
   java -jar target/test-assignment-0.0.1-SNAPSHOT.jar
   ```
   - Application starts on port **8080**.
   - Check endpoints at **`http://localhost:8080/videos`** (JSON responses).

---

## **2. Key Design Decisions**

1. **Modules**:  
   - The application is modular to keep entities, DTOs, and services separate.  
   - This structure improves maintainability.  

2. **JPA Entities**:  
   - Tables (e.g., **`Video`**, **`Engagement`**) are mapped to Java classes.  
   - JPA handles most database operations with minimal SQL.  

3. **DTOs (Data Transfer Objects)**:  
   - Expose only necessary data through DTOs instead of entities.  
   - Enhances **flexibility** in the API.  

4. **Direct Database Updates for Counters**:  
   - Counters (impressions/views) are incremented in a single SQL statement.  
   - Prevents **race conditions** and ensures **atomic updates**.

5. **Flyway Migrations**:  
   - Database schema changes are managed via versioned SQL files in `db/migration`.  
---
