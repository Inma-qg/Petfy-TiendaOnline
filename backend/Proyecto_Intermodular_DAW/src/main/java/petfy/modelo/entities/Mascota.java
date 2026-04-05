package petfy.modelo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    private String nombre;
    private TipoAnimal tipoAnimal;
    private String raza;
    private int edadAnios;
    private double pesoKg;

    @Enumerated(EnumType.STRING)
    private Tamanio tamanio;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_actividad")
    private NivelActividad nivelActividad;

    private String alergias;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;


    // Enums
    public enum TipoAnimal {
        PERRO, GATO, ROEDOR, AVE, PEZ, REPTIL, OTRO
    }

    public enum Tamanio {
        MINI, PEQUEÑO, MEDIANO, GRANDE, GIGANTE
    }

    public enum NivelActividad {
        BAJO, MEDIO, ALTO, MUY_ALTO
    }

 


  
}