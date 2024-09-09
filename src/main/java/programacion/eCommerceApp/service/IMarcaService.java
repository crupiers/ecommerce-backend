package programacion.ejemplo.service;

import programacion.ejemplo.DTO.MarcaDTO;
import programacion.ejemplo.model.Marca;

import java.util.List;

public interface IMarcaService {

    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public MarcaDTO guardar(MarcaDTO model);

    public Marca guardar(Marca model);

    public void eliminar(Marca model);
}
