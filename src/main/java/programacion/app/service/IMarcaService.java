package programacion.app.service;

import java.util.List;

import programacion.app.DTO.MarcaDTO;
import programacion.app.model.Marca;

public interface IMarcaService {

    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public MarcaDTO guardar(MarcaDTO model);

    public Marca guardar(Marca model);

    public void eliminar(Marca model);
}
