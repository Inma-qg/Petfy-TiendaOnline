import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  usuario: any = null;
  editando: boolean = false;
  
  datosEdicion = {
    nombre: '',
    apellidos: '',
    telefono: '',
    direccion: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    // Verificar que esté logueado
    if (!this.authService.isLoggedIn()) {
      alert('Debes iniciar sesión para ver tu perfil');
      this.router.navigate(['/login']);
    }
  }

  ngOnInit(): void {
    this.usuario = this.authService.getUsuario();
    this.cargarDatosEdicion();
  }

  cargarDatosEdicion(): void {
    if (this.usuario) {
      this.datosEdicion = {
        nombre: this.usuario.nombre || '',
        apellidos: this.usuario.apellidos || '',
        telefono: this.usuario.telefono || '',
        direccion: this.usuario.direccion || ''
      };
    }
  }

  activarEdicion(): void {
    this.editando = true;
  }

  cancelarEdicion(): void {
    this.editando = false;
    this.cargarDatosEdicion();
  }

  guardarCambios(): void {
    // En producción, aquí harías PUT al backend
    alert('Funcionalidad en desarrollo.\n\nDatos a guardar:\n' + JSON.stringify(this.datosEdicion, null, 2));
    this.editando = false;
  }

  cambiarPassword(): void {
    const nuevaPassword = prompt('Introduce tu nueva contraseña (mínimo 6 caracteres):');
    if (nuevaPassword && nuevaPassword.length >= 6) {
      alert('Funcionalidad en desarrollo.\nEn producción, cambiaría la contraseña.');
    } else if (nuevaPassword) {
      alert('La contraseña debe tener al menos 6 caracteres');
    }
  }
}