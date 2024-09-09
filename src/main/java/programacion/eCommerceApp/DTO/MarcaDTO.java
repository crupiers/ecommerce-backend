package programacion.eCommerceApp.DTO;


public class MarcaDTO {

    private Integer id;
    private String denominacion;
    private String observaciones;
    private int estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public MarcaDTO(Integer id, String denominacion, String observaciones, int estado) {
        this.id = id;
        this.denominacion = denominacion;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public MarcaDTO() {
    }
}
