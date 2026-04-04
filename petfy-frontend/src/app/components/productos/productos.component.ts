import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto.model';
import { CarritoService } from '../../services/carrito.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  productosFiltrados: Producto[] = [];
  cargando: boolean = true;
  error: string = '';
  busqueda: string = '';

  constructor(
    private productoService: ProductoService,
    private carritoService: CarritoService) { }

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.getAll().subscribe({
      next: (data) => {
        this.productos = data;
        this.productosFiltrados = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar productos. Verifica que el backend esté corriendo en http://localhost:8090';
        this.cargando = false;
        console.error('Error:', err);
      }
    });
  }

  buscar(): void {
    if (this.busqueda.trim() === '') {
      this.productosFiltrados = this.productos;
    } else {
      this.productosFiltrados = this.productos.filter(p => 
        p.nombre.toLowerCase().includes(this.busqueda.toLowerCase())
      );
    }
  }

  agregarAlCarrito(producto: any): void {
    this.carritoService.agregarProducto(producto, 1);
    alert(`${producto.nombre} añadido al carrito`);
  }
}
