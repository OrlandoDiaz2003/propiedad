# Propiedad Service

Este microservicio se encarga de la gestión de propiedades, permitiendo crear, consultar, modificar, buscar y eliminar registros de propiedades.

## Tecnologías
- Java 21
- Spring Boot
- Spring Data JPA
- MariaDB
- Lombok

---

## Configuración y Ejecución

### Variables de Entorno
Para ejecutar el proyecto, es necesario configurar las credenciales de la base de datos.
1. Copia el archivo `.env.example` y cámbialo a `.env`.
2. Edita el archivo `.env` con tus credenciales locales:
   ```env
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=nombre_bd
   DB_USER=usuario
   DB_PASSWORD=contraseña
   ```

### Makefile
El proyecto incluye un `Makefile` para facilitar la ejecución de comandos comunes de Maven Wrapper:

- `make run`: Ejecuta la aplicación.
- `make build`: Limpia y genera el paquete JAR.
- `make compile`: Compila el código fuente.
- `make test`: Ejecuta las pruebas unitarias.
- `make clean`: Limpia el directorio `target`.

---

## Endpoints

### 1. Obtener Propiedad por ID
Obtiene los detalles de una propiedad específica.

- **URL:** `/api/v0/propiedad/{id}`
- **Método:** `GET`
- **Respuesta Exitosa (200 OK):**
  ```json
  {
    "id": 1,
    "cantidadBaños": 2,
    "cantidadHabitaciones": 3,
    "metraje": 120,
    "direccion": "Calle Falsa 123",
    "estado": "Disponible",
    "tipo": "Casa",
    "ciudad": "Santiago",
    "numeroUnidad": "A-1",
    "fotosUrl": ["http://link-a-foto.com/1.jpg"]
  }
  ```

### 2. Crear Propiedad
Registra una nueva propiedad en el sistema.

- **URL:** `/api/v0/propiedad/`
- **Método:** `POST`
- **Cuerpo de la Petición (JSON):**
  ```json
  {
    "direccion": "Avenida Siempre Viva 742",
    "cantidadBaños": 2,
    "cantidadHabitaciones": 3,
    "metraje": 100,
    "idVendedor": 1,
    "idTipoPropiedad": 1,
    "idEstadoPropiedad": 1,
    "idCiudad": 5,
    "numeroUnidad": "101",
    "idCliente": null
  }
  ```
- **Notas sobre Parámetros:**
  - **Opcionales:** `numeroUnidad` (dependiendo del tipo), `idCliente`.
  - **Requeridos:** Todos los demás campos mostrados arriba.
  - **`numeroUnidad`:** Es **obligatorio** si el tipo de propiedad es **Departamento** o **Hotel**. Para otros tipos (como Casa), este campo se ignora o se establece como nulo automáticamente.

### 3. Modificar Propiedad
Actualiza parcialmente los datos de una propiedad existente.

- **URL:** `/api/v0/propiedad/{id}`
- **Método:** `PATCH`
- **Cuerpo de la Petición (JSON):**
  ```json
  {
    "cantidadHabitaciones": 4,
  }
  ```
- **Notas sobre Parámetros:**
  - **Todos los parámetros son opcionales** (`cantidadHabitaciones`, `cantidadBaños`, `metraje`, `estadoPropiedad`, `idCliente`). Solo se actualizarán los campos enviados.

### 4. Buscar Propiedades
Busca propiedades con filtros y paginación.

- **URL:** `/api/v0/propiedad/`
- **Método:** `GET`
- **Parámetros (Query Params):**
  - Todos son **opcionales**: `direccion`, `ciudad`, `metrajeMin`, `metrajeMax`, `tipoPropiedad`, `page`, `size`.

### 5. Eliminar Propiedad
- **URL:** `/api/v0/propiedad/{id}`
- **Método:** `DELETE`

---

## Manejo de Errores

El servicio cuenta con un gestor global de excepciones que captura tres casos principales:

### 1. Entidad No Encontrada (404 Not Found)
Se lanza cuando se intenta acceder, modificar o eliminar un recurso (Propiedad, Ciudad, Tipo, etc.) mediante un ID que no existe en la base de datos.

**Ejemplo de respuesta:**
```json
{
  "fecha_hora": "2026-05-05T15:30:00.000000",
  "estado": "NOT_FOUND",
  "error": "no encontrado",
  "mensaje": "No se ha encontrado propiedad con ID 99"
}
```

### 2. Error de Validación de Argumentos (400 Bad Request)
Se lanza cuando los datos enviados en el cuerpo de la petición (`@Valid`) no cumplen con las restricciones de las anotaciones (ej. `@NotBlank`, `@Min`, `@Max`). Devuelve un mapa detallado con cada campo fallido y su motivo.

**Ejemplo de respuesta:**
```json
{
  "fecha": "2026-05-05T15:30:00.000000",
  "estado": 400,
  "error": {
    "cantidadHabitaciones": "La propiedad debe tener minimo una habitacion",
    "direccion": "La direccion no puede estar vacia"
  }
}
```

### 3. Error de Argumento Ilegal / Lógica de Negocio (400 Bad Request)
Se lanza cuando los datos son sintácticamente correctos pero violan reglas de negocio, como:
- Intentar registrar una dirección que ya existe.
- Omitir el `numeroUnidad` en departamentos u hoteles.
- Rangos de búsqueda inválidos (ej. `metrajeMin` mayor que `metrajeMax`).

**Ejemplo de respuesta:**
```json
{
  "fecha / hora": "2026-05-05T15:30:00.000000",
  "estado": 400,
  "error": "Las propiedades tipo departamento deben tener un numero de unidad/habitacion"
}
```
