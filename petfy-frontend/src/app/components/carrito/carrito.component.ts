import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CarritoService, ItemCarrito } from '../../services/carrito.service';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {
  items: ItemCarrito[] = [];
  total: number = 0;

  constructor(
    public carritoService: CarritoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carritoService.carrito$.subscribe(items => {
      this.items = items;
      this.calcularTotal();
    });
  }

  calcularTotal(): void {
    this.total = this.carritoService.calcularTotal();
  }

  actualizarCantidad(productoId: number, cantidad: number): void {
    if (cantidad > 0) {
      this.carritoService.actualizarCantidad(productoId, cantidad);
    }
  }

  eliminar(productoId: number): void {
    if (confirm('¿Eliminar este producto del carrito?')) {
      this.carritoService.eliminarProducto(productoId);
    }
  }

  vaciarCarrito(): void {
    if (confirm('¿Vaciar todo el carrito?')) {
      this.carritoService.vaciarCarrito();
    }
  }

  continuarComprando(): void {
    this.router.navigate(['/productos']);
  }

  realizarPedido(): void {
    alert('Funcionalidad de pago en desarrollo.\n\nTotal a pagar: ' + this.total.toFixed(2) + '€');
  }
}