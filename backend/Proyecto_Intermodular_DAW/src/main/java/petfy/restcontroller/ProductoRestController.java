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
import petfy.modelo.entities.Producto;
import petfy.modelo.service.ProductoService;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoRestController {
	
	
	private final ProductoService pService;
	
	 @GetMapping
	    public ResponseEntity<List<Producto>> obtenerTodos() {
	        List<Producto> productos = pService.obtenerTodos();
	        return ResponseEntity.ok(productos);
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
	        Producto producto = pService.obtenerPorId(id);
	        return ResponseEntity.ok(producto);
	    }
	
	 /*
	  * GET /api/productos/buscar?nombre=xxx
	  * Buscar productos por nombre
	  */
	    @GetMapping("/buscar")
	    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
	        List<Producto> productos = pService.buscarPorNombre(nombre);
	        return ResponseEntity.ok(productos);
	    }
	    
	    /*
	     * GET /api/productos/filtrar?categoriaId=1&tipoAnimal=PERRO&precioMin=10&precioMax=50
	     * Búsqueda avanzada con filtros
	     */
	    @GetMapping("/filtrar")
	    public ResponseEntity<List<Producto>> filtrar(
	            @RequestParam(required = false) Integer categoriaId,
	            @RequestParam(required = false) Producto.TipoAnimal tipoAnimal,
	            @RequestParam(required = false) Producto.TamanioMascota tamanio,
	            @RequestParam(required = false) Double precioMin,
	            @RequestParam(required = false) Double precioMax) {
	        
	        List<Producto> productos = pService.busquedaAvanzada(
	                categoriaId, tipoAnimal, tamanio, precioMin, precioMax);
	        return ResponseEntity.ok(productos);
	    }
	 
	    
	//SOLO PARA ADMIN
	    
	    /*
	     * POST /api/productos
	     * Crear nuevo producto 
	     */
	    @PostMapping
	    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
	        Producto nuevoProducto = pService.crear(producto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
	    }
	    
	    /*
	     * PUT /api/productos/{id}
	     * Actualizar producto 
	     */
	    @PutMapping("/{id}")
	    public ResponseEntity<Producto> actualizar(
	            @PathVariable Integer id,
	            @Valid @RequestBody Producto producto) {
	        Producto productoActualizado = pService.actualizar(id, producto);
	        return ResponseEntity.ok(productoActualizado);
	    }
	    
	    /*
	     * DELETE /api/productos/{id}
	     * Eliminar producto
	     */
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
	        pService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	 

}
