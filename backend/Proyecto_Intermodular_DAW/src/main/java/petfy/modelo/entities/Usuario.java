package petfy.modelo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String telefono;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.CLIENTE;

    @CreationTimestamp
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    private Boolean activo = true;

    // Relaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mascota> mascotas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();


    // Enums
    public enum Rol {
        CLIENTE, ADMIN
    }

    // Método para no exponer la contraseña en JSON
    public String getPassword() {
        return password;
    }

    //Otros métodos
    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
        mascota.setUsuario(this);
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setUsuario(this);
    }
}