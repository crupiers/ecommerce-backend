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
  <summary>Servicio</summary>
2. [Servicio](#servicio---ecommerce-app)
  
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

<details>
  <summary>Entity</summary>
3. [Entity](#entity---ecommerce-app)

  - [Métodos](#métodos)
    - [Eliminar](#eliminar)
    - [Recuperar](#recuperar)
  - [Anotaciones](#anotaciones)
  - [Notas importantes](#notas-importantes)

</details>

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


# Servicio - eCommerce App

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
- **Retorno**: `{entity}Response` con los datos del `{entity}` creado.

### 4. Actualizar un {entity} existente
#### `{entity}Response actualizar(New{entity}Request new{entity}Request, Integer id)`
Actualiza un `{entity}` existente utilizando su ID.

- **Descripción**: 
  - Convierte el objeto `New{entity}Request` a una entidad `{entity}`.
  - Busca el `{entity}` por su ID. Si no se encuentra, lanza una excepción.
  - Actualiza los datos del `{entity}` y guarda los cambios en la base de datos.
- **Parámetros**: 
  - `new{entity}Request` – Un objeto que contiene los nuevos datos del `{entity}`.
  - `id` (Integer) – El identificador del `{entity}` a actualizar.
- **Retorno**: `{entity}Response` con los datos actualizados del `{entity}`.

### 5. Eliminar un {entity}
#### `void eliminar({entity} model)`
Elimina lógicamente un `{entity}` existente.

- **Descripción**: 
  - Cambia el estado del `{entity}` a `ELIMINADO` utilizando el método `eliminar()`.
  - Actualiza el registro de la entidad en la base de datos.
- **Parámetro**: `model` – La instancia del `{entity}` que se desea eliminar.

### 6. Recuperar un {entity} eliminado
#### `void recuperar({entity} model)`
Recupera un `{entity}` que ha sido eliminado lógicamente.

- **Descripción**: 
  - Cambia el estado del `{entity}` a `COMUN` utilizando el método `recuperar()`.
  - Actualiza el registro de la entidad en la base de datos.
- **Parámetro**: `model` – La instancia del `{entity}` que se desea recuperar.

## Notas importantes

- **Implementación de la interfaz**: Esta clase establece un contrato que debe ser cumplido por cualquier clase que implemente el servicio para la entidad `{entity}`. Asegura que se implementen los métodos necesarios para la gestión de datos de `{entity}`.
- **Uso de Optional**: Se utiliza la clase `Optional` para manejar la posibilidad de que un `{entity}` no se encuentre en la base de datos, lo que permite un manejo más seguro de valores nulos.
- **Manejo de excepciones**: La clase lanza excepciones específicas cuando se intenta crear un `{entity}` que ya existe o al intentar actualizar un `{
