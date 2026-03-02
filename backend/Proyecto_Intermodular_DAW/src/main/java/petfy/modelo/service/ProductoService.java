package petfy.modelo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import petfy.modelo.entities.Producto;

public interface ProductoService extends UserDetailsService{
	
	List<Producto> obtenerTodos();
	Producto obtenerPorId(Integer id);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> busquedaAvanzada(
            Integer categoriaId,
            Producto.TipoAnimal tipoAnimal,
            Producto.TamanioMascota tamanio,
            Double precioMin,
            Double precioMax);

    Producto crear(Producto producto);
    Producto actualizar(Integer id, Producto productoActualizado);
    void eliminar(Integer id);


    //Reducir stock (al realizar un pedido)

    void reducirStock(Integer id, Integer cantidad);

  //Aumentar stock (al realizar un pedido)
    void aumentarStock(Integer id, Integer cantidad);

  
}
