# Historial de Prompts y Cambios

Este archivo documenta la secuencia de prompts y cambios realizados durante el desarrollo del proyecto.

## 1. Construcción de Servicio de Notificación
```
Eres un experto en Kafka y en Java webflux y tienes que hacer un sistema gestion de inventario para una cadena de tiendas minoristas, cada una tiene su base de datos y cada 15 minutos se actualiza con la base de datos central. Actualmente tienes 3 microservicios: 
1. Product: donde se crean los productos 
2. Store: donde se administran las tiendas 
3. Inventory: donde se sincroniza el stock
Vas a crear un nuevo microservicio que se llame notifacion usando este plugin https://github.com/bancolombia/scaffold-clean-architecture para hacer la sincronización de la base de datos con postgres
```
- Definición inicial del sistema con:
  - Arquitectura de microservicios
  - Sincronización periódica de bases de datos
  - Integración con Kafka
  - Uso de WebFlux para programación reactiva
  - Scaffold de Clean Architecture

## 2. Generación del README inicial
```
Genera un README que explique el diseño de la API, los puntos principales, las instrucciones de configuracion
```
- Se creó el README.md inicial con:
  - Descripción de la arquitectura de microservicios
  - Tecnologías utilizadas
  - Estructura del proyecto
  - Configuración del entorno
  - API Endpoints
  - Instrucciones de instalación
  - Monitoreo y logging
  - Testing y contribución

## 3. Creación del Schema de Base de Datos
```
En la carpeta de driven adapter, hay una carpeta llamada data, segun los campos que hay, crea la base de datos llamada inventory_db y  la estructura para crear la tabla en una base de datos postgres
```
- Se creó schema.sql con:
  - Tablas para productos, tiendas, inventarios
  - Claves primarias y foráneas
  - Índices optimizados
  - Timestamps de auditoría


## 4. Generación de Datos de Ejemplo
```
Adicional, agrega 10 insert de cada tabla. Para la tabla Products en product_code seria ponerle un prefijo tipo "PRO00" y para la tabla Store en store_code ponerle un prefijo "STO00"
```
- Se agregaron datos de ejemplo:
  - 10 productos con códigos PRO001-PRO010
  - 10 tiendas con códigos STO001-STO010
  - 10 registros de inventario con estados variados
  - 10 notificaciones con diferentes tipos de eventos
  - 10 registros de sincronización
  - Datos realistas y coherentes entre tablas
  - Referencias correctas entre entidades usando UUIDs

## 5. Creación del Archivo de Prompts
```
crea un archivo llamado prompts.md y agrega los prompts que te he hecho
```
- Se creó prompts.md para:
  - Documentar la historia del desarrollo
  - Mantener registro de cambios
  - Explicar el contexto de cada modificación
  - Servir como referencia para futuros desarrollos

## Resultados Finales

El proyecto ahora cuenta con una arquitectura completa y documentada:

1. **Arquitectura de Microservicios**
   - Product Service (Gestión de productos)
   - Store Service (Administración de tiendas)
   - Inventory Service (Sincronización de stock)
   - Notification Service (Sistema de notificaciones)

2. **Documentación Técnica**
   - README completo y detallado
   - Instrucciones de configuración
   - Guía de instalación
   - Documentación de API endpoints
   - Historial de prompts y decisiones técnicas

3. **Schema de Base de Datos (inventory_db)**
   - Diseño normalizado de tablas
   - UUIDs como identificadores únicos
   - Relaciones y constraints definidos
   - Índices optimizados para consultas frecuentes
   - Triggers para mantenimiento de timestamps

4. **Tablas Principales**
   - tbl_products (Catálogo de productos)
   - tbl_stores (Información de tiendas)
   - tbl_inventories (Control de stock)
   - tbl_notifications (Sistema de notificaciones)
   - tbl_inventory_records (Registros de sincronización)

5. **Datos de Ejemplo**
   - 10 productos con diferentes categorías
   - 10 tiendas en diferentes ubicaciones
   - 10 registros de inventario
   - 10 notificaciones de ejemplo
   - 10 registros de sincronización

6. **Características Técnicas**
   - Clean Architecture implementada
   - Programación reactiva con WebFlux
   - Integración con Kafka
   - Base de datos reactiva (R2DBC)
   - Sistema de sincronización periódica

7. **Monitoreo y Seguridad**
   - Endpoints de Actuator configurados
   - Tracing distribuido
   - Métricas con Prometheus
   - Logging centralizado

Cada componente fue implementado siguiendo las mejores prácticas de desarrollo, manteniendo:
- Principios SOLID
- Clean Architecture
- Diseño orientado al dominio
- Patrones reactivos
- Escalabilidad horizontal
- Mantenibilidad del código