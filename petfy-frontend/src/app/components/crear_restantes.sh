#!/bin/bash

# Producto Detalle Component
cat > producto-detalle/producto-detalle.component.ts << 'EOTS'
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto.model';

@Component({
  selector: 'app-producto-detalle',
  templateUrl: './producto-detalle.component.html',
  styleUrls: ['./producto-detalle.component.css']
})
export class ProductoDetalleComponent implements OnInit {
  producto?: Producto;
  cargando: boolean = true;
  error: string = '';

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarProducto(id);
  }

  cargarProducto(id: number): void {
    this.productoService.getById(id).subscribe({
      next: (data) => {
        this.producto = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar el producto.';
        this.cargando = false;
        console.error('Error:', err);
      }
    });
  }
}
EOTS

cat > producto-detalle/producto-detalle.component.html << 'EOHTML'
<div class="container mt-4">
  <div *ngIf="cargando" class="text-center">
    <div class="spinner-border text-primary" role="status"></div>
  </div>

  <div *ngIf="error" class="alert alert-danger">{{ error }}</div>

  <div *ngIf="producto && !cargando" class="row">
    <div class="col-md-6">
      <img 
        [src]="producto.imagenUrl || 'assets/placeholder.jpg'" 
        class="img-fluid rounded" 
        alt="{{ producto.nombre }}">
    </div>
    <div class="col-md-6">
      <h2>{{ producto.nombre }}</h2>
      <p class="text-muted">
        <i class="bi bi-tag"></i> 
        {{ producto.categoria?.nombre || 'Sin categoría' }}
      </p>
      <hr>
      <p>{{ producto.descripcion }}</p>
      <h3 class="text-primary">{{ producto.precio | currency:'EUR' }}</h3>
      
      <div class="mb-3">
        <span class="badge bg-success" *ngIf="producto.stock > 0">
          <i class="bi bi-check-circle"></i> En stock: {{ producto.stock }} unidades
        </span>
        <span class="badge bg-danger" *ngIf="producto.stock === 0">
          <i class="bi bi-x-circle"></i> Sin stock
        </span>
      </div>

      <div class="d-grid gap-2">
        <button class="btn btn-primary btn-lg" [disabled]="producto.stock === 0">
          <i class="bi bi-cart-plus"></i> Añadir al Carrito
        </button>
        <button class="btn btn-outline-secondary" routerLink="/productos">
          <i class="bi bi-arrow-left"></i> Volver a Productos
        </button>
      </div>
    </div>
  </div>
</div>
EOHTML

echo "/* Estilos detalle */" > producto-detalle/producto-detalle.component.css

# Categorias Component
cat > categorias/categorias.component.ts << 'EOTS'
import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { Categoria } from '../../models/categoria.model';

@Component({
  selector: 'app-categorias',
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.css']
})
export class CategoriasComponent implements OnInit {
  categorias: Categoria[] = [];
  cargando: boolean = true;
  error: string = '';

  constructor(private categoriaService: CategoriaService) { }

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.categoriaService.getAll().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar categorías.';
        this.cargando = false;
      }
    });
  }
}
EOTS

cat > categorias/categorias.component.html << 'EOHTML'
<div class="container mt-4">
  <h2><i class="bi bi-tags"></i> Categorías</h2>
  
  <div *ngIf="cargando" class="text-center">
    <div class="spinner-border text-primary"></div>
  </div>

  <div *ngIf="error" class="alert alert-danger">{{ error }}</div>

  <div class="row mt-4" *ngIf="!cargando && !error">
    <div class="col-md-4 mb-3" *ngFor="let categoria of categorias">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">
            <i class="bi bi-tag-fill text-primary"></i> 
            {{ categoria.nombre }}
          </h5>
          <p class="card-text text-muted">{{ categoria.descripcion }}</p>
          <a routerLink="/productos" class="btn btn-outline-primary">
            Ver Productos
          </a>
        </div>
      </div>
    </div>
  </div>
</div>
EOHTML

echo "/* Estilos categorías */" > categorias/categorias.component.css

# Acerca De Component
cat > acerca-de/acerca-de.component.ts << 'EOTS'
import { Component } from '@angular/core';

@Component({
  selector: 'app-acerca-de',
  templateUrl: './acerca-de.component.html',
  styleUrls: ['./acerca-de.component.css']
})
export class AcercaDeComponent {
}
EOTS

cat > acerca-de/acerca-de.component.html << 'EOHTML'
<div class="container mt-4">
  <h2><i class="bi bi-info-circle"></i> Acerca de PETFY</h2>
  
  <div class="row mt-4">
    <div class="col-md-8">
      <p class="lead">
        PETFY es una tienda online dedicada al bienestar de las mascotas, 
        desarrollada como Trabajo de Fin de Grado del ciclo de Desarrollo 
        de Aplicaciones Web.
      </p>
      
      <h4 class="mt-4">Nuestro Proyecto</h4>
      <p>
        Este proyecto utiliza tecnologías modernas para crear una experiencia 
        de compra online completa y funcional:
      </p>
      <ul>
        <li><strong>Frontend:</strong> Angular con Bootstrap</li>
        <li><strong>Backend:</strong> Spring Boot (Java)</li>
        <li><strong>Base de Datos:</strong> MySQL</li>
      </ul>

      <h4 class="mt-4">Equipo de Desarrollo</h4>
      <p>
        <strong>Desarrolladores:</strong> Aida Reyes, Alejandro Pineda, Inmaculada Quilón
        <br>
        <strong>Tutor:</strong> César Carrión
        <br>
        <strong>Centro:</strong> UNIR FP
      </p>
    </div>
    <div class="col-md-4">
      <div class="card bg-light">
        <div class="card-body">
          <h5 class="card-title">Contacto</h5>
          <p class="card-text">
            <i class="bi bi-envelope"></i> info@petfy.com<br>
            <i class="bi bi-telephone"></i> 900 123 456<br>
            <i class="bi bi-geo-alt"></i> Valencia, España
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
EOHTML

echo "/* Estilos acerca de */" > acerca-de/acerca-de.component.css

echo "Todos los componentes restantes creados!"
