// src/app/services/auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface LoginResponse {
  success: boolean;
  usuario?: {
    id: number;
    nombre: string;
    apellidos?: string;
    email: string;
    rol: string;
  };
  mensaje: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/auth';
  
  // Observable para saber si hay usuario logueado
  private usuarioLogueadoSubject = new BehaviorSubject<any>(null);
  public usuarioLogueado$ = this.usuarioLogueadoSubject.asObservable();

  constructor(private http: HttpClient) {
    // Al iniciar, verificar si hay usuario guardado en localStorage
    const usuarioGuardado = localStorage.getItem('usuario');
    if (usuarioGuardado) {
      this.usuarioLogueadoSubject.next(JSON.parse(usuarioGuardado));
    }
  }

  /**
   * Login de usuario
   */
  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap(response => {
          if (response.success && response.usuario) {
            // Guardar usuario en localStorage
            localStorage.setItem('usuario', JSON.stringify(response.usuario));
            
            // Notificar a los observadores
            this.usuarioLogueadoSubject.next(response.usuario);
          }
        })
      );
  }

  /**
   * Registro de nuevo usuario
   */
  registro(usuario: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/registro`, usuario)
      .pipe(
        tap(response => {
          if (response.success && response.usuario) {
            localStorage.setItem('usuario', JSON.stringify(response.usuario));
            this.usuarioLogueadoSubject.next(response.usuario);
          }
        })
      );
  }

  /**
   * Logout
   */
  logout(): void {
    localStorage.removeItem('usuario');
    this.usuarioLogueadoSubject.next(null);
  }

  /**
   * Verificar si hay usuario logueado
   */
  isLoggedIn(): boolean {
    return localStorage.getItem('usuario') !== null;
  }

  /**
   * Obtener usuario actual
   */
  getUsuario(): any {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }

  /**
   * Verificar si es admin
   */
  isAdmin(): boolean {
    const usuario = this.getUsuario();
    return usuario && usuario.rol === 'ADMIN';
  }

  /**
   * Verificar si es cliente
   */
  isCliente(): boolean {
    const usuario = this.getUsuario();
    return usuario && usuario.rol === 'CLIENTE';
  }
}