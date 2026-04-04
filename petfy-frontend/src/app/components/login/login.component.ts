// src/app/components/login/login.component.ts

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  error: string = '';
  cargando: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    // Si ya está logueado, redirigir a productos
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/productos']);
    }
  }

  login(): void {
    // Validaciones básicas
    if (!this.email || !this.password) {
      this.error = 'Por favor, completa todos los campos';
      return;
    }

    this.error = '';
    this.cargando = true;

    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        if (response.success) {
          console.log('Login exitoso:', response.usuario);
          // Redirigir a productos
          this.router.navigate(['/productos']);
        } else {
          this.error = response.mensaje;
          this.cargando = false;
        }
      },
      error: (err) => {
        console.error('Error en login:', err);
        this.error = err.error?.mensaje || 'Error al iniciar sesión. Verifica tus credenciales.';
        this.cargando = false;
      }
    });
  }

  irARegistro(): void {
    this.router.navigate(['/registro']);
  }
}