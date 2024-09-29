# Controller - eCommerce App

Esta clase pertenece al controlador `{entity}Controller` de la aplicación eCommerce desarrollada con Spring Boot. La clase gestiona operaciones relacionadas con la entidad `{entity}`, tales como listar, buscar, crear, actualizar, eliminar y recuperar {entity}. El servicio principal encargado de la lógica de negocio es `I{entity}Service`.

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
- **Excepción**: Si el {entity} no existe o ya ha sido eliminado, se lanza un `ResponseStatusException`.

## Dependencias de la clase

- **Logger**: Se utiliza el logger `LoggerFactory` para informar sobre las acciones ejecutadas.
- **I{entity}Service**: El servicio encargado de la lógica de negocio relacionada con la entidad `{entity}`. Las dependencias de este servicio son inyectadas automáticamente mediante `@Autowired`.
- **{entity}Mapper**: Utilizado para mapear la entidad `{entity}` a `{entity}Response` y evitar exponer la entidad completa.

## Notas importantes

- **Sin método de actualización de {entity}**: Dado que la entidad `{entity}` solo contiene el nombre del {entity}, no se requiere un método de actualización completo.
- **Control de acceso**: El controlador tiene habilitado `@CrossOrigin` para permitir acceso desde `http://localhost:8080`.
