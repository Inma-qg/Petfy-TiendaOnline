package petfy.modelo.service;

import java.util.List;

import petfy.modelo.entities.Categoria;

public interface CategoriaService {
	

    List<Categoria> obtenerTodas();
    Categoria obtenerPorId(Integer id);
    Categoria buscarPorNombre(String nombre);
    Categoria crear(Categoria categoria);
    Categoria actualizar(Integer id, Categoria categoriaActualizada);
    int eliminar(Integer id);



}
