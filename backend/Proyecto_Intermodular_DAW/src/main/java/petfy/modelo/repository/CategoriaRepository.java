package petfy.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import petfy.modelo.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{


  
    Optional<Categoria> findByNombre(String nombre);
    List<Categoria> findByActivaTrue();
    List<Categoria> findByCategoriaPadreIsNull();

    // Buscar categorías principales activas
    List<Categoria> findByCategoriaPadreIsNullAndActivaTrue();

    // Buscar subcategorías de una categoría
    //List<Categoria> findByCategoriaPadreId(Integer categoriaPadreId);

    // Buscar subcategorías activas de una categoría
    List<Categoria> findByCategoriaPadreIdAndActivaTrue(Integer categoriaPadreId);

    // Query personalizada: Categorías con número de productos
    @Query("SELECT c FROM Categoria c LEFT JOIN c.productos p " +
           "WHERE c.activa = true " +
           "GROUP BY c.id " +
           "ORDER BY COUNT(p.id) DESC")
    List<Categoria> findCategoriasConProductos();
}
