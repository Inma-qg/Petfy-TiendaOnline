
import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CarritoService } from '../../services/carrito.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  usuarioLogueado: any = null;
  dropdownAbierto: boolean = false;
  cantidadCarrito: number = 0;

  constructor(
    private authService: AuthService,
    private carritoService: CarritoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Suscribirse a cambios en el usuario logueado
    this.authService.usuarioLogueado$.subscribe(usuario => {
      this.usuarioLogueado = usuario;
    });

    // Suscribirse a cambios en el carrito
    this.carritoService.carrito$.subscribe(items => {
      this.cantidadCarrito = items.reduce((total, item) => total + item.cantidad, 0);
    });
  }

  toggleDropdown(): void {
    this.dropdownAbierto = !this.dropdownAbierto;
  }

  cerrarDropdown(): void {
    this.dropdownAbierto = false;
  }

  // Cerrar dropdown al hacer click fuera
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.dropdown')) {
      this.dropdownAbierto = false;
    }
  }

  logout(): void {
    this.authService.logout();
    this.cerrarDropdown();
    this.router.navigate(['/login']);
  }
}