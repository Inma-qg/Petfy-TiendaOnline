import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface ItemCarrito {
  producto: any;
  cantidad: number;
}

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private carritoSubject = new BehaviorSubject<ItemCarrito[]>([]);
  public carrito$ = this.carritoSubject.asObservable();

  constructor() {
    
    const carritoGuardado = localStorage.getItem('carrito');
    if (carritoGuardado) {
      this.carritoSubject.next(JSON.parse(carritoGuardado));
    }
  }

  /**
   * Obtener items del carrito
   */
  getItems(): ItemCarrito[] {
    return this.carritoSubject.value;
  }

  /**
   * Añadir producto al carrito
   */
  agregarProducto(producto: any, cantidad: number = 1): void {
    const items = this.getItems();
    const itemExistente = items.find(item => item.producto.id === producto.id);

    if (itemExistente) {
      // Si ya existe, incrementar cantidad
      itemExistente.cantidad += cantidad;
    } else {
      // Si no existe, añadir nuevo
      items.push({ producto, cantidad });
    }

    this.actualizarCarrito(items);
  }

  /**
   * Actualizar cantidad de un producto
   */
  actualizarCantidad(productoId: number, cantidad: number): void {
    const items = this.getItems();
    const item = items.find(item => item.producto.id === productoId);

    if (item) {
      item.cantidad = cantidad;
      if (item.cantidad <= 0) {
        this.eliminarProducto(productoId);
        return;
      }
      this.actualizarCarrito(items);
    }
  }

  /**
   * Eliminar producto del carrito
   */
  eliminarProducto(productoId: number): void {
    const items = this.getItems().filter(item => item.producto.id !== productoId);
    this.actualizarCarrito(items);
  }

  /**
   * Vaciar carrito
   */
  vaciarCarrito(): void {
    this.actualizarCarrito([]);
  }

  /**
   * Calcular total del carrito
   */
  calcularTotal(): number {
    return this.getItems().reduce((total, item) => {
      return total + (item.producto.precio * item.cantidad);
    }, 0);
  }

  /**
   * Obtener cantidad total de productos
   */
  getCantidadTotal(): number {
    return this.getItems().reduce((total, item) => total + item.cantidad, 0);
  }

  /**
   * Actualizar carrito y guardar en localStorage
   */
  private actualizarCarrito(items: ItemCarrito[]): void {
    this.carritoSubject.next(items);
    localStorage.setItem('carrito', JSON.stringify(items));
  }
}