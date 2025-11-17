# **TP Cloud – Documentación**

## **Descripción General**

Este proyecto corresponde al **Trabajo Práctico de Cloud (4to año – 2025)**.
Consiste en el backend de una plataforma inmobiliaria completa que incluye:

* CRUD de propiedades con geolocalización.
* Sistema de autenticación y autorización utilizando **Spring Security**.
* Integración con modelos de Inteligencia Artificial para generar descripciones automáticas de propiedades.
* Base de datos en **Azure PostgreSQL** en entorno de producción.
* Aplicación desplegada en una **VPS de Azure** conectada mediante Docker, con balanceo/reverse proxy por Nginx.
* Frontend desarrollado en **React**, servido por Nginx dentro del mismo stack de Docker Compose.

La IA utiliza la versión gratuita de la API de **OpenRouter.ai**, lo cual puede limitar la calidad o longitud de algunas respuestas.

---

# **Arquitectura del Proyecto**

La arquitectura final incluye **3 componentes principales**:

### **1. Frontend (React + Nginx)**

* El proyecto de React se buildéa y se sirve desde un contenedor Nginx.
* Dentro del contenedor se configura **Nginx como Reverse Proxy**, redirigiendo las rutas de API hacia el backend.
* Esto permite que `/api/**` sea dirigido automáticamente al contenedor del backend.

### **2. Backend (Spring Boot)**

* Framework: **Spring Boot 3**
* Seguridad: **Spring Security + JWT**
* Acceso a datos: **Spring Data JPA + Hibernate**
* Perfiles configurados:

  * `dev`: base H2 en memoria
  * `prod`: PostgreSQL en Azure
* Integración con la API de IA mediante OpenRouter para generar descripciones automáticas de propiedades.

### **3. Base de Datos (Azure PostgreSQL)**

Producción utiliza una base de datos PostgreSQL gestionada por Azure:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://cloudbdd.postgres.database.azure.com:5432/postgres
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
```

En desarrollo no es necesaria una base externa ya que se usa H2.

### **4. Infraestructura – VPS en Azure**

El sistema completo corre en una **VPS de Azure**, accesible en:

```
usuario@20.169.204.198
```

Los contenedores se ejecutan con **Docker Compose**, levantando:

* Contenedor del backend (Spring Boot)
* Contenedor del frontend (React + Nginx)
* Red bridge interna entre ambos

---

# **Modelos Principales**

### **AppUser**

Representa un usuario del sistema con roles para Spring Security (`ROLE_ADMIN`, `ROLE_USER`, etc.).

### **Location**

Entidad para guardar puntos geográficos y enlaces de referencia.

### **Property**

Entidad principal de la inmobiliaria.

Incluye:

* Título
* Descripción generada por IA
* Imagen
* Precio
* Superficie
* Cantidad de habitaciones/baños
* Ubicación
* Enlace de Google Maps

---

# **Integración con la API de IA – OpenRouter.ai**

Se utiliza el endpoint:

```
https://openrouter.ai/api/v1/chat/completions
```

Desde el backend (Java) se envían prompts personalizados para generar descripciones automáticas basadas en:

* Ubicación
* Superficie
* Cantidad de ambientes
* Estilo del inmueble
* Datos específicos del usuario o agente

Esta integración permite que el usuario simplemente cargue los datos básicos y la IA produzca automáticamente una **descripción atractiva y lista para publicar**.

---

# **Modo Desarrollo (Dev)**

El modo development utiliza el perfil:

```
spring.profiles.active=dev
```

En este modo:

* Base de datos: **H2 en memoria**
* No se requiere Docker
* El backend puede ejecutarse desde el IDE o usando Maven

### **Requisitos**

Instalar Java 21 y Maven:

```bash
sudo apt install openjdk-21-jdk
sudo apt install maven
```

### **Correr el backend en dev**

Desde tu IDE (IntelliJ, VSCode) o mediante:

```bash
mvn spring-boot:run
```

---

# **Modo Producción (Prod)**

En producción se usa:

```
spring.profiles.active=prod
```

Conexión a Azure PostgreSQL y despliegue en contenedores Docker.

---

# **Compilar y Construir Imagen Docker**

Desde la raíz del backend:

```bash
sudo docker build -t ianvid/cloudback:latest .
```

Esto genera la imagen lista para ser usada en Docker Compose.

---

# **Docker Compose**

El sistema completo se levanta mediante `docker-compose.yml`, que incluye:

* **backend** → contenedor Spring Boot
* **frontend** → contenedor Nginx + React
* **reverse proxy interno**
* **red compartida**
* variables de entorno de producción

```yaml
# Docker compose seguro que solo expone el puerto 80 para el frontend con nginx. 

services:
  # Dado una imagen de backend ya generada, le seteo variables de entorno para que se pueda conectar a la DB (estas variables son recibidas en application.properties de springboot)
  # El backend se expone en la red interna puerto 8080 (no se puede acceder desde el exterior, ni desde el propio host del contenedor)
  back:
    image: ianvid/cloudback:latest
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://cloudbdd.postgres.database.azure.com:5432/postgres
      - SPRING_DATASOURCE_USERNAME=#####
      - SPRING_DATASOURCE_PASSWORD=#####
      - OPENROUTER_API_KEY=######
      - JWT_KEY=############
      - OPENROUTER_MODEL=google/gemma-3n-e2b-it:free
    networks:
      - internal
    expose:
      - "8080"
    # Descomentar para depuracion y poder acceder directamente al backend sin pasar por nginx
    # ports:
    #   - "8080:8080"

# Opcional: integra una imagen existente de frontend (con nginx) que escucha en el puerto 80.
# Esta imagen de frontend react+vite ya viene con un nginx escuchando en el puerto 80 el cual devuelve el front construido.
  front:
    image: ianvid/cloudfront:latest 
    ports:
      - "80:80" 
    networks:
      - internal
      - public
    depends_on:
      - back

volumes:
  sql_app_name:

networks:
  internal:
    driver: bridge
  public:
    driver: bridge
```