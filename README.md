# ColorController - eCommerce App

Esta clase pertenece al controlador `ColorController` de la aplicación eCommerce desarrollada con Spring Boot. La clase gestiona operaciones relacionadas con la entidad `Color`, tales como listar, buscar, crear, actualizar, eliminar y recuperar colores. El servicio principal encargado de la lógica de negocio es `IColorService`.

## Endpoints disponibles

### 1. Obtener todos los colores
#### `GET /eCommerce/color`
Este endpoint devuelve una lista de todos los colores disponibles. La entidad `ColorResponse` es utilizada para retornar solo la información necesaria, en lugar de exponer directamente la entidad `Color`.

- **Retorno**: Lista de `ColorResponse`.
- **Log**: El logger informa al usuario que se han obtenido todos los colores disponibles.

### 2. Buscar color por ID
#### `GET /eCommerce/color/{id}`
Busca un color por su ID. Si el color no existe o ha sido eliminado lógicamente, se lanza una excepción personalizada.

- **Parámetro**: `id` (Integer) – El identificador del color.
- **Retorno**: `ColorResponse` con la información del color.
- **Excepción**: Si el color no es encontrado o ha sido eliminado, se lanza un `ResponseStatusException` con un mensaje de error.

### 3. Crear un nuevo color
#### `POST /eCommerce/color`
Permite crear un nuevo color. La solicitud debe contener un cuerpo JSON con los datos del color, validados mediante `@Valid` y la clase `NewColorRequest`.

- **Cuerpo de la solicitud**: JSON con los datos del color.
- **Retorno**: `ColorResponse` con el color creado.
- **Estado HTTP**: `201 Created` si el color se ha creado correctamente.

### 4. Actualizar un color existente
#### `PUT /eCommerce/color/actualizar/{id}`
Actualiza un color existente mediante su ID. Se utiliza el cuerpo de la solicitud para recibir los nuevos datos del color.

- **Parámetro**: `id` (Integer) – El identificador del color a actualizar.
- **Cuerpo de la solicitud**: JSON con los nuevos datos del color.
- **Retorno**: `ColorResponse` con los datos actualizados.
- **Estado HTTP**: `202 Accepted` si la actualización fue exitosa.

### 5. Recuperar un color eliminado
#### `PUT /eCommerce/color/recuperar/{id}`
Este endpoint permite recuperar un color que haya sido eliminado lógicamente. No devuelve datos adicionales, simplemente realiza la acción de recuperación.

- **Parámetro**: `id` (Integer) – El identificador del color a recuperar.
- **Retorno**: `ResponseEntity<Void>` sin cuerpo.
- **Excepción**: Si el color no existe en la base de datos, se lanza un `ResponseStatusException`.

### 6. Eliminar un color
#### `DELETE /eCommerce/color/{id}`
Permite eliminar lógicamente un color existente. Si el color no existe, se lanza una excepción.

- **Parámetro**: `id` (Integer) – El identificador del color a eliminar.
- **Retorno**: `ResponseEntity<Void>` sin cuerpo.
- **Excepción**: Si el color no existe o ya ha sido eliminado, se lanza un `ResponseStatusException`.

## Dependencias de la clase

- **Logger**: Se utiliza el logger `LoggerFactory` para informar sobre las acciones ejecutadas.
- **IColorService**: El servicio encargado de la lógica de negocio relacionada con la entidad `Color`. Las dependencias de este servicio son inyectadas automáticamente mediante `@Autowired`.
- **ColorMapper**: Utilizado para mapear la entidad `Color` a `ColorResponse` y evitar exponer la entidad completa.

## Notas importantes

- **Sin método de actualización de color**: Dado que la entidad `Color` solo contiene el nombre del color, no se requiere un método de actualización completo.
- **Control de acceso**: El controlador tiene habilitado `@CrossOrigin` para permitir acceso desde `http://localhost:8080`.
