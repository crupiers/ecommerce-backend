package programacion.eCommerceApp.controller.response;

public record CategoriaResponse(
    Integer id,
    String nombre,
    String descripcion,
    int estado,
    String createdBy,
    String createdAt,
    String updatedBy,
    String updatedAt,
    String deletedAt
) {
}
