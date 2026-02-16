# 🐎 MarketHorses — Backend MVP

Backend MVP para la gestión de anuncios de venta de caballos.
Este proyecto modela el dominio principal del sistema y aplica reglas de negocio claras siguiendo una arquitectura en capas.

---

## 📌 Descripción

**MarketHorses** es un backend desarrollado con **Spring Boot** que permite a usuarios con rol `SELLER` gestionar anuncios de venta de caballos.

El sistema actualmente permite:

* Crear anuncios
* Editar anuncios en estado `BORRADOR`
* Registrar auditoría de acciones exitosas
* Aplicar reglas de negocio a nivel de dominio

Este MVP representa la base estructural del sistema y está diseñado para ser fácilmente extensible.

---

## 🏗 Arquitectura

El proyecto sigue una arquitectura en capas:

```
Controller
   ↓
Service (Application Layer)
   ↓
Domain (Business Rules)
   ↓
AuditService
```

### Principios aplicados

* Separación de responsabilidades
* Lógica de negocio en el dominio (no en el controller)
* Reglas explícitas y protegidas
* Auditoría desacoplada
* Diseño preparado para persistencia real futura (JPA)

---

## ⚙️ Stack Tecnológico

* Java 17+
* Spring Boot
* Arquitectura en capas
* Persistencia simulada en memoria (`List`)
* Maven

---

## 🧠 Modelo de Dominio

### Entidades

* `User`
* `Horse`
* `Ad`
* `AuditLog`

### Estados del Anuncio

Actualmente soportado:

* `BORRADOR`

Preparado para:

* `PUBLICADO`
* `RECHAZADO`
* `VENDIDO`

---

## 🔁 Flujo Funcional Actual (SELLER)

1. El usuario `SELLER` crea un anuncio.
2. El anuncio nace en estado `BORRADOR`.
3. Puede editarlo mientras permanezca en ese estado.
4. Cada operación exitosa genera un registro en `AuditLog`.

---

## 🔒 Reglas de Negocio Implementadas

* Solo usuarios con rol `SELLER` pueden crear anuncios.
* Solo el dueño del anuncio puede editarlo.
* Solo anuncios en estado `BORRADOR` pueden modificarse.
* Solo se auditan operaciones exitosas.

Las reglas viven en el **dominio**, no en el controller.

---

## 🌐 Endpoints Principales

> Base URL: `/ads`

### Crear anuncio

```
POST /ads
```

Body de ejemplo:

```json
{
  "sellerId": 1,
  "horseId": 10,
  "price": 5000
}
```

---

### Editar anuncio

```
PUT /ads/{adId}
```

Body de ejemplo:

```json
{
  "price": 5500
}
```

---

## ▶️ Cómo ejecutar el proyecto

### 1️⃣ Clonar repositorio

```bash
git clone https://github.com/IngRodrigoPena/markethorses.git
```

### 2️⃣ Entrar al proyecto

```bash
cd markethorses
```

### 3️⃣ Ejecutar

```bash
mvn spring-boot:run
```

La aplicación se levantará en:

```
http://localhost:8080
```

---

## 🧪 Estado del Proyecto — Semana 1

* ✔ Flujo SELLER funcional
* ✔ Control de estados
* ✔ Auditoría activa
* ✔ Reglas de negocio en dominio
* ✔ Código limpio y entendible
* ✔ Base lista para persistencia real

---

## 🧩 Decisiones de Diseño

* Se utilizó persistencia en memoria para enfocarse en dominio y reglas.
* La auditoría está desacoplada para permitir futura integración con:

    * Base de datos
    * Kafka / Event streaming
    * Microservicios
* El modelo está preparado para escalar hacia arquitectura hexagonal.

---

## 🚀 Próximos Pasos

* Implementar persistencia con JPA / Hibernate
* Agregar autenticación y autorización (JWT)
* Soporte para nuevos estados (`PUBLICADO`, `RECHAZADO`)
* Validaciones más robustas
* Tests unitarios y de integración
* Dockerización
* Documentación con Swagger / OpenAPI

---

## 📊 Diagrama Simplificado

```
[AdController]
        ↓
[AdService] -----> [AuditService]
        ↓
        ↓
        [Ad]
            ├── Horse
            └── User (Seller)
```

---

## 🎯 Objetivo del Proyecto

Este proyecto fue desarrollado como ejercicio de diseño backend enfocado en:

* Modelado de dominio
* Aplicación de reglas de negocio
* Separación de capas
* Buenas prácticas en Spring Boot

---

