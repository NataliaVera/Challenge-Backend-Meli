# Challenge Backend Mercado Libre

Este proyecto implementa una arquitectura de microservicios para gestionar un sistema de comercio electrónico, siguiendo los principios de Clean Architecture y diseño reactivo.

## Arquitectura

El sistema está compuesto por cuatro microservicios principales:

### 1. Microservicio de Productos (ms-product)
- Gestión del catálogo de productos
- CRUD de productos
- Búsqueda y filtrado de productos
- Validación de datos de productos

### 2. Microservicio de Inventario (ms-inventory)
- Control de stock de productos
- Actualización de inventario en tiempo real
- Reserva de inventario durante compras
- Notificaciones de bajo stock

### 3. Microservicio de Tienda (ms-store)
- Gestión de transacciones de compra
- Procesamiento de órdenes
- Estado de pedidos
- Integración con inventario

### 4. Microservicio de Notificaciones (ms-notification)
- Sistema de notificaciones asíncrono
- Integración con Kafka para mensajería
- Notificaciones de estado de pedidos
- Alertas de inventario

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring WebFlux** (Programación reactiva)
- **R2DBC** (Base de datos reactiva)
- **PostgreSQL**
- **Apache Kafka**
- **Gradle**
- **Docker**
- **Clean Architecture**

## Estructura del Proyecto

Cada microservicio sigue la estructura de Clean Architecture:

```
microservice/
├── applications/
│   └── app-service/         # Configuración de la aplicación
├── domain/
│   ├── model/              # Entidades y objetos de dominio
│   └── usecase/            # Casos de uso y lógica de negocio
└── infrastructure/
    ├── driven-adapters/    # Adaptadores para DB, mensajería, etc.
    ├── entry-points/       # Controllers y endpoints
    └── helpers/            # Utilidades y configuraciones
```

## Configuración del Entorno

### Prerequisitos

- Java 17 o superior
- Docker y Docker Compose
- PostgreSQL
- Apache Kafka

### Pasos de Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/NataliaVera/Challenge-Backend-Meli.git
   cd Challenge-Backend-Meli
   ```

2. **Configurar las bases de datos:**
   ```bash
   docker-compose up -d postgres kafka
   ```

3. **Compilar los microservicios:**
   ```bash
   ./gradlew clean build
   ```

4. **Ejecutar los microservicios:**
   ```bash
   ./gradlew :ms-product:bootRun
   ./gradlew :ms-inventory:bootRun
   ./gradlew :ms-store:bootRun
   ./gradlew :ms-notification:bootRun
   ```

### Estructura de Base de Datos

El esquema de la base de datos se encuentra en el archivo `database/schema.sql`. Este archivo contiene:

- Creación de tablas con UUIDs como identificadores
- Relaciones entre las tablas
- Índices optimizados
- Datos de ejemplo pre-cargados
- Triggers para actualización automática de timestamps

Para inicializar la base de datos:

1. Crear una base de datos en PostgreSQL
2. Ejecutar el script de schema:
   ```bash
   psql -U username -f database/schema.sql
   ```

El schema incluye las siguientes tablas:
- `tbl_products`: Catálogo de productos
- `tbl_stores`: Información de tiendas
- `tbl_inventories`: Control de inventario
- `tbl_notifications`: Sistema de notificaciones
- `tbl_inventory_records`: Registros de sincronización

### Variables de Entorno

Cada microservicio requiere las siguientes variables de entorno:

```properties
# Database
SPRING_R2DBC_URL=r2dbc:postgresql://localhost:5432/inventory_db
SPRING_R2DBC_USERNAME=username
SPRING_R2DBC_PASSWORD=password

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

## API Endpoints

### Productos (ms-product)
- `GET /api/products` - Listar productos
- `GET /api/products/{id}` - Obtener producto por ID
- `POST /api/products` - Crear nuevo producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

### Inventario (ms-inventory)
- `GET /api/inventory/{productId}` - Consultar stock
- `PUT /api/inventory/{productId}` - Actualizar stock
- `POST /api/inventory/reserve` - Reservar inventario

### Tienda (ms-store)
- `POST /api/orders` - Crear orden
- `GET /api/orders/{orderId}` - Consultar orden
- `PUT /api/orders/{orderId}/status` - Actualizar estado de orden

### Notificaciones (ms-notification)
- `GET /api/notifications` - Consultar notificaciones
- `POST /api/notifications/subscribe` - Suscribirse a notificaciones

## Monitoreo y Logging

- Actuator endpoints para monitoreo
- Tracing distribuido con Sleuth
- Métricas con Prometheus
- Logs centralizados con ELK Stack

## Testing

Para ejecutar las pruebas:

```bash
./gradlew test
```

## Contribución

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.