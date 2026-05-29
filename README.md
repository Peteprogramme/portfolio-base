## Technical Challenges & Resolutions

### 1. Integration of JWT Authentication
* **Problem:** Initially, the API failed to intercept incoming requests correctly, causing authentication filters to be bypassed or misconfigured within the Spring Security chain.
* **Resolution:** Implemented a custom `JwtAuthenticationFilter` extending `OncePerRequestFilter`. Correctly registered it in `SecurityConfig` using `addFilterBefore(..., UsernamePasswordAuthenticationFilter.class)` and ensured the `SecurityContextHolder` was manually updated with the authentication token to grant authorized access.

### 2. Database Constraint Violations (SQL NOT NULL)
* **Problem:** API `POST` requests were failing with SQL integrity constraint violations (`Failing row contains...`). The request payloads were incomplete, missing mandatory fields like `manufacturer` and `minimumRequired`.
* **Resolution:** Aligned the DTO/Entity model with the database schema requirements. Updated the client-side JSON request body to include all mandatory fields and validated the mapping between the `InventoryItem` Java fields and the SQL columns.

### 3. Security Scope Logic
* **Problem:** The entire API was locked behind authentication, preventing clients from browsing the inventory catalog publicly.
* **Resolution:** Configured granular access control in `SecurityConfig` by allowing `HttpMethod.GET` on the inventory endpoint while maintaining strict protection for sensitive write operations (`POST`, `PUT`, `DELETE`).

---

## 🛠️ Troubleshooting Case Study: The 405 Method Not Allowed

* **The Problem:** During the implementation of the `DELETE` endpoint, integration tests returned a `405 Method Not Allowed` status, even though the controller logic was perfectly mapped.
* **The Root Cause:** Spring Boot's embedded Tomcat server does not hot-reload method-level structural changes by default. The server was running an old compiled instance that only recognized `GET`, `POST`, and `PUT`.
* **The Solution:** Halted the active container process and triggered a clean recompilation and restart of the application context. This successfully registered the `@DeleteMapping` annotation, resulting in a clean `204 No Content` response.
