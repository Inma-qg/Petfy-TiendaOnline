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
  cargando = true;
  error = '';

  constructor(private route: ActivatedRoute, private productoService: ProductoService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productoService.getById(id).subscribe({
      next: (data) => { this.producto = data; this.cargando = false; },
      error: (err) => { this.error = 'Error al cargar el producto.'; this.cargando = false; }
    });
  }
}
