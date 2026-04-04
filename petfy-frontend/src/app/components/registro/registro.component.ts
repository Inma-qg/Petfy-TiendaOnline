// src/app/components/registro/registro.component.ts

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  usuario = {
    nombre: '',
    apellidos: '',
    email: '',
    password: '',
    dni: '',
    telefono: '',
    direccion: ''
  };
  
  passwordConfirm: string = '';
  error: string = '';
  cargando: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  registrar(): void {
    // Validaciones básicas
    if (!this.usuario.nombre || !this.usuario.email || !this.usuario.password) {
      this.error = 'Por favor, completa los campos obligatorios';
      return;
    }

    if (this.usuario.password !== this.passwordConfirm) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    if (this.usuario.password.length < 6) {
      this.error = 'La contraseña debe tener al menos 6 caracteres';
      return;
    }

    this.error = '';
    this.cargando = true;

    this.authService.registro(this.usuario).subscribe({
      next: (response) => {
        if (response.success) {
          console.log('Registro exitoso:', response.usuario);
          // Redirigir a productos
          this.router.navigate(['/productos']);
        } else {
          this.error = response.mensaje;
          this.cargando = false;
        }
      },
      error: (err) => {
        console.error('Error en registro:', err);
        this.error = err.error?.mensaje || 'Error al registrarse. Intenta de nuevo.';
        this.cargando = false;
      }
    });
  }

  irALogin(): void {
    this.router.navigate(['/login']);
  }
}