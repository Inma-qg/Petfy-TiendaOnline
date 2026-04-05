package petfy.modelo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

 
    private String nombre;
    private String descripcion;
    private Double precio;
    private int stock = 0;
    private String imagenUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_animal")
    private TipoAnimal tipoAnimal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tamanio_mascota")
    private TamanioMascota tamanioMascota;

    @Column(name = "peso_producto_kg")
    private double pesoProductoKg;

    @Column(name = "duracion_estimada_dias")
    private int duracionEstimadaDias;

    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    // Relaciones

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<DetallePedido> detallesPedido = new ArrayList<>();

    // Enums
    public enum TipoAnimal {
        PERRO, GATO, ROEDOR, AVE, PEZ, REPTIL, OTRO, TODOS
    }

    public enum TamanioMascota {
        MINI, PEQUEÑO, MEDIANO, GRANDE, GIGANTE, TODOS
    }

    // Métodos de utilidad
    public boolean hayStock() {
        return stock > 0;
    }

    public boolean hayStock(Integer cantidad) {
        return stock >= cantidad;
    }

    public void reducirStock(Integer cantidad) {
        if (!hayStock(cantidad)) {
            throw new IllegalArgumentException("Stock insuficiente");
        }
        this.stock -= cantidad;
    }

    public void aumentarStock(Integer cantidad) {
        this.stock += cantidad;
    }

    

    
}