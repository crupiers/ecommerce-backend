# Índice

<details>
  <summary>Controller</summary>

1. [Controller](#controller---ecommerce-app)

  - [Anotaciones Utilizadas](#anotaciones-utilizadas)
  - [Endpoints disponibles](#endpoints-disponibles)
    - [Obtener todos los {entity}](#1-obtener-todos-los-entity)
    - [Buscar {entity} por ID](#2-buscar-entity-por-id)
    - [Crear un nuevo {entity}](#3-crear-un-nuevo-entity)
    - [Actualizar un {entity} existente](#4-actualizar-un-entity-existente)
    - [Recuperar un {entity} eliminado](#5-recuperar-un-entity-eliminado)
    - [Eliminar un {entity}](#6-eliminar-un-entity)
  - [Dependencias de la clase](#dependencias-de-la-clase)

</details>

<details>
  <summary>Entity</summary>
  
2. [Entity](#entity---ecommerce-app)

  - [Métodos](#métodos)
    - [Eliminar](#eliminar)
    - [Recuperar](#recuperar)
  - [Anotaciones](#anotaciones)
  - [Notas importantes](#notas-importantes)

</details>
<details>
  <summary>Servicio</summary>
  
3. [Servicio](#servicio---ecommerce-app)
  
  - [Anotaciones Utilizadas](#anotaciones-utilizadas-1)
  - [Dependencias](#dependencias)
  - [Métodos disponibles](#métodos-disponibles)
    - [Listar todos los {entity}](#1-listar-todos-los-entity)
    - [Buscar {entity} por ID](#2-buscar-entity-por-id-1)
    - [Crear un nuevo {entity}](#3-crear-un-nuevo-entity-1)
    - [Actualizar un {entity} existente](#4-actualizar-un-entity-existente-1)
    - [Eliminar un {entity}](#5-eliminar-un-entity-1)
    - [Recuperar un {entity} eliminado](#6-recuperar-un-entity-eliminado-1)

</details>

---

# Índice

1. [Controller](#controller---ecommerce-app)
2. [Servicio](#servicio---ecommerce-app)
3. [Entity](#entity---ecommerce-app)

---

# Controller - eCommerce App

La clase gestiona operaciones relacionadas con la entidad `{entity}`, tales como listar, buscar, crear, actualizar, eliminar y recuperar.

## Anotaciones Utilizadas

- **@RestController:** Define que esta clase es un controlador REST.
- **@RequestMapping("eCommerce"):** Mapea todos los endpoints bajo el prefijo `/eCommerce`.
- **@CrossOrigin(value="http://localhost:8080"):** Permite el acceso desde el puerto `8080` del localhost.

## Endpoints disponibles

### 1. Obtener todos los {entity}
#### `GET /eCommerce/{entity}`
Este endpoint devuelve una lista de todos los {entity} disponibles. La entidad `{entity}Response` es utilizada para retornar solo la información necesaria, en lugar de exponer directamente la entidad `{entity}`.

- **Retorno**: Lista de `{entity}Response`.
- **Log**: El logger informa al usuario que se han obtenido todos los {entity} disponibles.

### 2. Buscar {entity} por ID
#### `GET /eCommerce/{entity}/{id}`
Busca un {entity} por su ID. Si el {entity} no existe o ha sido eliminado lógicamente, se lanza una excepción personalizada.

- **Parámetro**: `id` (Integer) – El identificador del {entity}.
- **Retorno**: `{entity}Response` con la información del {entity}.
- **Excepción**: Si el {entity} no es encontrado o ha sido eliminado, se lanza un `ResponseStatusException` con un mensaje de error.

### 3. Crear un nuevo {entity}
#### `POST /eCommerce/{entity}`
Permite crear un nuevo {entity}. La solicitud debe contener un cuerpo JSON con los datos del {entity}, validados mediante `@Valid` y la clase `New{entity}Request`.

- **Cuerpo de la solicitud**: JSON con los datos del {entity}.
- **Retorno**: `{entity}Response` con el {entity} creado.
- **Estado HTTP**: `201 Created` si el {entity} se ha creado correctamente.

### 4. Actualizar un {entity} existente
#### `PUT /eCommerce/{entity}/actualizar/{id}`
Actualiza un {entity} existente mediante su ID. Se utiliza el cuerpo de la solicitud para recibir los nuevos datos del {entity}.

- **Parámetro**: `id` (Integer) – El identificador del {entity} a actualizar.
- **Cuerpo de la solicitud**: JSON con los nuevos datos del {entity}.
- **Retorno**: `{entity}Response` con los datos actualizados.
- **Estado HTTP**: `202 Accepted` si la actualización fue exitosa.

### 5. Recuperar un {entity} eliminado
#### `PUT /eCommerce/{entity}/recuperar/{id}`
Este endpoint permite recuperar un {entity} que haya sido eliminado lógicamente. No devuelve datos adicionales, simplemente realiza la acción de recuperación.

- **Parámetro**: `id` (Integer) – El identificador del {entity} a recuperar.
- **Retorno**: `ResponseEntity<Void>` sin cuerpo.
- **Excepción**: Si el {entity} no existe en la base de datos, se lanza un `ResponseStatusException`.

### 6. Eliminar un {entity}
#### `DELETE /eCommerce/{entity}/{id}`
Permite eliminar lógicamente un {entity} existente. Si el {entity} no existe, se lanza una excepción.

- **Parámetro**: `id` (Integer) – El identificador del {entity} a eliminar.
- **Retorno**: `ResponseEntity<Void>` sin cuerpo.
- **Excepción**: Si el {entity} no existe o ya ha sido eliminado, se lanza una excepción.

## Dependencias de la clase

- **Logger**: Se utiliza el logger `LoggerFactory` para informar sobre las acciones ejecutadas.
- **I{entity}Service**: El servicio encargado de la lógica de negocio relacionada con la entidad `{entity}`. Las dependencias de este servicio son inyectadas automáticamente mediante `@Autowired`.
- **{entity}Mapper**: Utilizado para mapear la entidad `{entity}` a `{entity}Response` y evitar exponer la entidad completa.

---
# Entity - eCommerce App

Esta clase representa la entidad `{entity}` en la aplicación eCommerce desarrollada con Spring Boot. La entidad `{entity}` es gestionada en la base de datos y contiene información como el `nombre`, el `estado` y su `id`. Además, utiliza varias etiquetas de Lombok y JPA para simplificar la creación y manipulación de objetos de esta clase.

## Métodos

### `eliminar()`
Este método cambia el estado del `{entity}` a `ELIMINADO`. Cuando se llama a este método, se marca la entidad como eliminada lógicamente, cambiando el valor del campo `estado` a `1`.

### `recuperar()`
Este método cambia el estado del `{entity}` a `COMUN`, indicando que ha sido recuperado de su estado eliminado. El campo `estado` vuelve a tomar el valor `0` cuando se llama a este método.

## Anotaciones

La clase `{entity}` incluye varias anotaciones para simplificar el desarrollo:

- **@Entity**: Indica que esta clase es una entidad de JPA, lo que significa que será registrada como una tabla en la base de datos.
  
- **@Data**: Anotación de Lombok que genera automáticamente getters, setters, `hashCode`, `equals` y `toString` para los atributos de la clase.
  
- **@Builder**: Permite implementar el patrón de diseño Builder para crear objetos `{entity}`. Esta anotación facilita la construcción de instancias de la clase sin tener que usar un constructor completo.

- **@ToString**: Genera un método `toString` que devuelve una representación en cadena de los atributos del objeto `{entity}`.

- **@AllArgsConstructor**: Genera un constructor que acepta todos los campos de la clase como parámetros.

- **@NoArgsConstructor**: Genera un constructor sin parámetros.

## Notas importantes

- **Estado por defecto**: El campo `estado` tiene un valor por defecto de `0`, que corresponde al estado común de la entidad `{entity}`.
- **Manejo del estado**: La entidad maneja dos posibles estados: `COMUN` (valor `0`) y `ELIMINADO` (valor `1`). Estos estados se utilizan para realizar eliminaciones lógicas sin eliminar el registro de la base de datos.
- **Uso de Lombok**: Se utilizan varias anotaciones de Lombok (`@Data`, `@Builder`, `@ToString`, entre otras) para simplificar la escritura del código, reduciendo la cantidad de código repetitivo en la clase.

---

# Service - eCommerce App

Esta clase representa el servicio `{entity}Service`, que implementa la interfaz `I{entity}Service` en la aplicación eCommerce desarrollada con Spring Boot. Esta clase se encarga de la lógica de negocio relacionada con la entidad `{entity}`, gestionando operaciones como listar, buscar, crear, actualizar, eliminar y recuperar `{entity}`.

## Anotaciones Utilizadas

- **@Service:** Indica que esta clase es un servicio en la arquitectura de Spring, lo que permite su detección automática y la inyección de dependencias.

## Dependencias

La clase `{entity}Service` utiliza el repositorio `I{entity}Repository` para interactuar con la base de datos. El controlador utiliza este servicio para gestionar las operaciones relacionadas con `{entity}`.

## Métodos disponibles

### 1. Listar todos los {entity}
#### `List<{entity}Response> listar()`
Este método devuelve una lista de todos los `{entity}` que no han sido eliminados lógicamente.

- **Descripción**: 
  - Utiliza el repositorio para buscar los `{entity}` visibles (con estado `COMUN`).
  - Convierte la lista de objetos `{entity}` a `{entity}Response` utilizando `{entity}Mapper`.
- **Retorno**: Lista de `{entity}Response`.

### 2. Buscar {entity} por ID
#### `{entity} buscarPorId(Integer id)`
Busca un `{entity}` utilizando su identificador único.

- **Descripción**: 
  - Utiliza el repositorio para buscar el `{entity}` por su ID. Si no se encuentra, devuelve `null`.
- **Parámetro**: `id` (Integer) – El identificador del `{entity}`.
- **Retorno**: Una instancia de `{entity}` si se encuentra, o `null` si no se encuentra.

### 3. Crear un nuevo {entity}
#### `{entity}Response crear(New{entity}Request new{entity}Request)`
Permite la creación de un nuevo `{entity}`.

- **Descripción**: 
  - Convierte el objeto `New{entity}Request` a una entidad `{entity}`.
  - Verifica si el `{entity}` ya existe en la base de datos. Si existe, lanza una excepción.
  - Si no existe, guarda el nuevo `{entity}` en la base de datos y devuelve un `{entity}Response`.
- **Parámetro**: `new{entity}Request` – Un objeto que contiene la información del nuevo `{entity}`.
- **Retorno**: `{entity}Response
