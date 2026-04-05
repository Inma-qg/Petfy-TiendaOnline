package petfy.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfy.modelo.entities.Categoria;
import petfy.modelo.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> obtenerTodas() {
		
		return categoriaRepository.findByActivaTrue();
	}

	@Override
	public Categoria obtenerPorId(Integer id) {
	    return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

	@Override
	public Categoria buscarPorNombre(String nombre) {
		
	     return categoriaRepository.findByNombre(nombre)
	                .orElseThrow(() -> new RuntimeException("Categoría no encontrada por nombre: " + nombre));
	    }

	@Override
	public Categoria crear(Categoria categoria) {
		 
	    return categoriaRepository.save(categoria);
	    }

	//Categoria lo he hecho distinto a producto porq nose cual me va a funcionar
	@Override
	public Categoria actualizar(Integer id, Categoria categoriaActualizada) {
		if (!categoriaRepository.existsById(id)) {
	        throw new RuntimeException("La categoría no existe");
	    }

	    categoriaActualizada.setId(id); 
	    return categoriaRepository.save(categoriaActualizada);
    }

	@Override
	public int eliminar(Integer id) {
		if (categoriaRepository.existsById(id))
			try {
				categoriaRepository.deleteById(id);
				return 1;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return -1;
			}
		else
			return 0;
	}

}
