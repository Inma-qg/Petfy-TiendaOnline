package petfy.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import petfy.modelo.entities.Producto;
import petfy.modelo.entities.Producto.TamanioMascota;
import petfy.modelo.entities.Producto.TipoAnimal;
import petfy.modelo.repository.ProductoRepository;


@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;


    @Override
    public List<Producto> obtenerTodos() {
    	
        return productoRepository.findByActivoTrue();
    }

	@Override
	public Producto obtenerPorId(Integer id) {
		
	     return productoRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
	    }

	 @Override
	    public List<Producto> buscarPorNombre(String nombre) {
		 
	        return productoRepository.findByNombreContainingIgnoreCase(nombre);
	    }

	@Override
	public List<Producto> busquedaAvanzada(Integer categoriaId, TipoAnimal tipoAnimal, TamanioMascota tamanio,
			Double precioMin, Double precioMax) {
		return ((ProductoServiceImpl) productoRepository).busquedaAvanzada(
                categoriaId, tipoAnimal, tamanio, precioMin, precioMax);
	}

	 @Override
	    public Producto crear(Producto producto) {
	    //esta a lo bruto, habría que hacer un try/catch
	        return productoRepository.save(producto);
	    }

	@Override
	public Producto actualizar(Integer id, Producto productoActualizado) {
		Producto productoExistente = obtenerPorId(id);
        
        // Actualizar campos
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setTipoAnimal(productoActualizado.getTipoAnimal());
        productoExistente.setTamanioMascota(productoActualizado.getTamanioMascota());
        productoExistente.setPesoProductoKg(productoActualizado.getPesoProductoKg());
        productoExistente.setDuracionEstimadaDias(productoActualizado.getDuracionEstimadaDias());
        productoExistente.setActivo(productoActualizado.getActivo());
        
        return productoRepository.save(productoExistente);
    }

	@Override
	public void eliminar(Integer id) {
		Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    public void reducirStock(Integer id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        
        if (!producto.hayStock(cantidad)) {
            throw new RuntimeException(
                "Stock insuficiente. Disponible: " + producto.getStock() + 
                ", Solicitado: " + cantidad);
        }
        
        producto.reducirStock(cantidad);
        productoRepository.save(producto);
    }

    @Override
    public void aumentarStock(Integer id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        producto.aumentarStock(cantidad);
        productoRepository.save(producto);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}



	/*
	 *  @Override
	*public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	*	
	*	return productoRepository.findById(username).orElse(null);
	*}
	 */
   
	

	

}
