package petfy.modelo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import petfy.modelo.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

	  // Buscar por nombre (case insensitive)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por categoría
    List<Producto> findByCategoriaId(int categoriaId);


    // Buscar solo productos activos
    List<Producto> findByActivoTrue();

    // Buscar por tipo de animal
    List<Producto> findByTipoAnimalAndActivoTrue(Producto.TipoAnimal tipoAnimal);

    // Buscar por tipo de animal y tamaño de mascota
    
    List<Producto> findByTipoAnimalAndTamanioMascotaAndActivoTrue(
        Producto.TipoAnimal tipoAnimal, 
        Producto.TamanioMascota tamanioMascota);
}
