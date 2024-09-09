package programacion.eCommerceApp.service;

import java.util.List;

import programacion.eCommerceApp.DTO.MarcaDTO;
import programacion.eCommerceApp.model.Marca;

public interface IMarcaService {

    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public MarcaDTO guardar(MarcaDTO model);

    public Marca guardar(Marca model);

    public void eliminar(Marca model);

    public void recuperar(Marca model);
}
