
---

# 🐎 MarketHorses — Backend Domain-Driven MVP

> Backend diseñado para modelar un sistema confiable de publicación y verificación de anuncios de venta de caballos.
> Enfoque principal: **dominio sólido, reglas de negocio explícitas y arquitectura limpia**.

---

## 🎯 Objetivo del Proyecto

MarketHorses fue desarrollado como ejercicio avanzado de diseño backend con foco en:

* Modelado estratégico del dominio
* Protección de reglas de negocio
* Separación estricta de capas
* Escalabilidad futura
* Preparación para persistencia real y seguridad

El sistema simula una plataforma donde los anuncios deben pasar por un proceso de **verificación de evidencias** antes de publicarse, garantizando confianza en el marketplace.

---

# 🏗 Arquitectura

El proyecto implementa una arquitectura en capas con clara separación de responsabilidades:

```text
Controller → Application Service → Domain → Repository
```

### Distribución interna

```text
CONTROLLERS
│
├── AdController
├── VerificationController
└── AuditController
│
SERVICES (Application Layer)
│
├── AdService
├── VerificationService
└── AuditService
│
DOMAIN (Core Business Logic)
│
├── Ad
├── Horse
├── Evidence
├── Verification
└── AuditLog
│
REPOSITORIES (Infraestructura)
│
├── InMemoryAdRepository
├── InMemoryVerificationRepository
└── List (auditoría)
```

---

## 🧠 Decisiones de Diseño

### 1️⃣ Reglas en el Dominio

Toda validación crítica vive dentro de las entidades y no en los controllers.

Ejemplos:

* Un anuncio solo puede editarse si está en `BORRADOR`.
* Solo el dueño puede modificarlo.
* No se permiten evidencias duplicadas.
* No se puede aprobar un anuncio sin pasar por `EN_VERIFICACION`.

Esto evita anemización del modelo.

---

### 2️⃣ Auditoría Desacoplada

El `AuditService` no interfiere con el flujo principal.
Solo registra acciones exitosas y puede evolucionar hacia:

* Base de datos
* Event streaming (Kafka)
* Microservicio independiente

---

### 3️⃣ Persistencia en Memoria (Decisión Estratégica)

Se utilizó almacenamiento en memoria para:

* Enfocarse en reglas de negocio
* Evitar distracciones de infraestructura
* Mantener el MVP simple y claro

El diseño permite reemplazar fácilmente por JPA/Hibernate.

---

# 🧩 Modelo de Dominio

## 📌 Ad (Aggregate Root)

Representa un anuncio de venta.

### Estados del ciclo de vida:

* `BORRADOR`
* `EN_VERIFICACION`
* `APROBADO`
* `RECHAZADO`
* `PUBLICADO`

Controla:

* Edición
* Evidencias
* Transiciones de estado
* Validaciones internas

---

## 🐎 Horse

Entidad asociada al anuncio:

* Nombre
* Raza
* Edad
* Dueño

---

## 📎 Evidence

Evidencias requeridas para verificación.

### Tipos soportados:

* PHOTO
* VETERINARY_CERTIFICATE
* PEDIGREE
* OWNERSHIP_DOCUMENT
* VIDEO

### Estados:

* PENDIENTE_VERIFICACION
* VERIFICADO
* RECHAZADO

---

## 📊 AuditLog

Registra:

* Creación
* Edición
* Subida de evidencias
* Envío a verificación
* Aprobación / Rechazo

---

# 🔄 Flujo del Anuncio

```text
BORRADOR
   │
   ├── Editar
   ├── Agregar Evidencias
   │
   ▼
EN_VERIFICACION
   │
   ├── Aprobar → APROBADO → PUBLICADO
   │
   └── Rechazar → RECHAZADO → BORRADOR
```

Este flujo garantiza control total del ciclo de vida del anuncio.

---

# 🔒 Reglas de Negocio Implementadas

### Crear Anuncio

* Solo `SELLER`.

### Editar Anuncio

* Solo dueño.
* Solo en estado `BORRADOR`.

### Evidencias

* Solo en `BORRADOR`.
* Sin duplicados.
* Requeridas para verificación.

### Envío a Verificación

* Cambia estado a `EN_VERIFICACION`.

### Aprobar / Rechazar

* Solo `ADMIN`.
* Solo en `EN_VERIFICACION`.

### Auditoría

* Solo acciones exitosas son registradas.

---

# 🌐 Endpoints Principales

## Ads

```http
POST   /ads
PUT    /ads/{id}
GET    /ads
GET    /ads/{id}
```

## Evidencias

```http
POST /ads/{id}/evidence
```

## Verificación

```http
POST /ads/{id}/submit
POST /ads/{id}/approve?userId=1
POST /ads/{id}/reject?userId=1
```

## Auditoría

```http
GET /audit
```

---

# ⚙️ Stack Tecnológico

* Java 17+
* Spring Boot
* Maven
* Arquitectura en capas
* Persistencia en memoria

---

# 📈 Estado Actual

✔ Flujo completo SELLER → ADMIN
✔ Control estricto de estados
✔ Dominio protegido
✔ Auditoría desacoplada
✔ Código limpio y extensible
✔ Preparado para persistencia real

---

# 🚀 Roadmap Técnico

* Implementar JPA / Hibernate
* JWT + Spring Security
* DTO Layer
* Tests unitarios y de integración
* Dockerización
* Swagger / OpenAPI
* Evolución hacia arquitectura hexagonal

---

# 🧠 Qué Demuestra Este Proyecto

Este proyecto demuestra:

* Capacidad de modelar dominio real
* Aplicación correcta de reglas de negocio
* Separación de responsabilidades
* Pensamiento arquitectónico
* Diseño preparado para escalar

---



````
## Manejo de Errores en la API

La API implementa una estrategia centralizada de manejo de excepciones utilizando `@RestControllerAdvice`.  
Este enfoque permite gestionar los errores de forma consistente en todos los endpoints.

### Excepciones personalizadas

Se han definido las siguientes excepciones personalizadas:

- `NotFoundException`
- `BusinessException`
- `UnauthorizedException`

### Estructura de respuesta de errores

Todas las respuestas de error de la API siguen una estructura uniforme:

```json
{
  "error": "ERROR_TYPE",
  "message": "Descripción del error",
  "timestamp": "ISO_DATE"
}
````

### Beneficios

* Mantiene una **estructura de respuesta consistente** para todos los errores.
* Facilita la **integración para los consumidores de la API** al proporcionar respuestas predecibles.
* Mejora la **mantenibilidad y escalabilidad** del código al centralizar el manejo de excepciones.

```

