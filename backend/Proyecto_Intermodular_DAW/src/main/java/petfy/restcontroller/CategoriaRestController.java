package petfy.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import petfy.modelo.entities.Categoria;
import petfy.modelo.service.CategoriaService;


@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor

public class CategoriaRestController {
	
	
	private final CategoriaService cService;

   /* CategoriaRestController(CategoriaServiceImpl categoriaServiceImpl) {
        this.categoriaServiceImpl = categoriaServiceImpl;
		this.cService = null;
    }
    */
	
	@GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = cService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer id) {
        Categoria categoria = cService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

   
    @GetMapping("/buscar")
    public ResponseEntity<Categoria> buscarPorNombre(@RequestParam String nombre) {
        Categoria categoria = cService.buscarPorNombre(nombre);
        return ResponseEntity.ok(categoria);
    }

    
    //SOLO PARA ADMIN
    /*
     * POST /api/categorias
     * Crear nueva categoría 
     */
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria) {
        Categoria nuevaCategoria = cService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    /*
     * PUT /api/categorias/{id}
     * Actualizar categoría
     */
    @PutMapping("/{id}")
    public Categoria actualizar(
            @PathVariable Integer id,
            @RequestBody Categoria categoria) {

        return cService.actualizar(id, categoria);
    }

    /*
     * DELETE /api/categorias/{id}
     * Eliminar categoría 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    
	
	

}
