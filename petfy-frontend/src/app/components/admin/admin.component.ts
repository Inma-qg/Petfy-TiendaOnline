// src/app/components/admin/admin.component.ts

import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  vistaActual: string = 'dashboard';
  
  // Estadísticas
  totalProductos: number = 0;
  totalCategorias: number = 0;
  productosActivos: number = 0;
  productosSinStock: number = 0;
  
  // Lista de productos
  productos: any[] = [];
  categorias: any[] = [];
  cargando: boolean = false;
  error: string = '';
  mensaje: string = '';
  
  // Modo edición
  productoEditando: any = null;
  categoriaEditando: any = null;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private authService: AuthService,
    private router: Router
  ) {
    // Verificar que sea admin
    if (!this.authService.isAdmin()) {
      alert('Acceso denegado. Solo administradores.');
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.cargarEstadisticas();
  }

  cambiarVista(vista: string): void {
    this.vistaActual = vista;
    this.mensaje = '';
    this.error = '';
    if (vista === 'productos') {
      this.cargarProductos();
    } else if (vista === 'categorias') {
      this.cargarCategorias();
    }
  }

  cargarEstadisticas(): void {
    this.productoService.getAll().subscribe({
      next: (productos) => {
        this.totalProductos = productos.length;
        this.productosActivos = productos.filter(p => p.activo).length;
        this.productosSinStock = productos.filter(p => p.stock === 0).length;
      }
    });

    this.categoriaService.getAll().subscribe({
      next: (categorias) => {
        this.totalCategorias = categorias.length;
      }
    });
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.getAll().subscribe({
      next: (data) => {
        this.productos = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar productos';
        this.cargando = false;
      }
    });
  }

  cargarCategorias(): void {
    this.cargando = true;
    this.categoriaService.getAll().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar categorías';
        this.cargando = false;
      }
    });
  }

  // ============== PRODUCTOS ==============

  editarProducto(producto: any): void {
    this.productoEditando = { ...producto }; // Clonar para editar
  }

  cancelarEdicionProducto(): void {
    this.productoEditando = null;
  }

  guardarProducto(): void {
    if (!this.productoEditando) return;

    this.cargando = true;
    this.productoService.update(this.productoEditando.id, this.productoEditando).subscribe({
      next: (productoActualizado) => {
        // Actualizar en la lista
        const index = this.productos.findIndex(p => p.id === productoActualizado.id);
        if (index !== -1) {
          this.productos[index] = productoActualizado;
        }
        this.mensaje = 'Producto actualizado correctamente';
        this.productoEditando = null;
        this.cargando = false;
        
        // Recargar estadísticas
        this.cargarEstadisticas();
      },
      error: (err) => {
        this.error = 'Error al actualizar producto: ' + (err.error?.message || err.message);
        this.cargando = false;
      }
    });
  }

  eliminarProducto(id: number, nombre: string): void {
    if (!confirm(`¿Estás seguro de eliminar "${nombre}"?`)) {
      return;
    }

    this.cargando = true;
    this.productoService.delete(id).subscribe({
      next: () => {
        // Eliminar de la lista
        this.productos = this.productos.filter(p => p.id !== id);
        this.mensaje = 'Producto eliminado correctamente';
        this.cargando = false;
        
        // Recargar estadísticas
        this.cargarEstadisticas();
      },
      error: (err) => {
        this.error = 'Error al eliminar producto: ' + (err.error?.message || err.message);
        this.cargando = false;
      }
    });
  }

  // ============== CATEGORÍAS ==============

  editarCategoria(categoria: any): void {
    this.categoriaEditando = { ...categoria };
  }

  cancelarEdicionCategoria(): void {
    this.categoriaEditando = null;
  }

  guardarCategoria(): void {
    if (!this.categoriaEditando) return;

    this.cargando = true;
    this.categoriaService.update(this.categoriaEditando.id, this.categoriaEditando).subscribe({
      next: (categoriaActualizada) => {
        // Actualizar en la lista
        const index = this.categorias.findIndex(c => c.id === categoriaActualizada.id);
        if (index !== -1) {
          this.categorias[index] = categoriaActualizada;
        }
        this.mensaje = 'Categoría actualizada correctamente';
        this.categoriaEditando = null;
        this.cargando = false;
        
        // Recargar estadísticas
        this.cargarEstadisticas();
      },
      error: (err) => {
        this.error = 'Error al actualizar categoría: ' + (err.error?.message || err.message);
        this.cargando = false;
      }
    });
  }

  eliminarCategoria(id: number, nombre: string): void {
    if (!confirm(`¿Estás seguro de eliminar la categoría "${nombre}"?\n\nADVERTENCIA: Esto puede afectar a los productos asociados.`)) {
      return;
    }

    this.cargando = true;
    this.categoriaService.delete(id).subscribe({
      next: () => {
        // Eliminar de la lista
        this.categorias = this.categorias.filter(c => c.id !== id);
        this.mensaje = 'Categoría eliminada correctamente';
        this.cargando = false;
        
        // Recargar estadísticas
        this.cargarEstadisticas();
      },
      error: (err) => {
        this.error = 'Error al eliminar categoría: ' + (err.error?.message || err.message);
        this.cargando = false;
      }
    });
  }
}