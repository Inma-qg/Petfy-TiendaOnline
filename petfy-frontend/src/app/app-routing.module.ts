import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProductosComponent } from './components/productos/productos.component';
import { ProductoDetalleComponent } from './components/producto-detalle/producto-detalle.component';
import { CategoriasComponent } from './components/categorias/categorias.component';
import { AcercaDeComponent } from './components/acerca-de/acerca-de.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'productos', component: ProductosComponent },
  { path: 'productos/:id', component: ProductoDetalleComponent },
  { path: 'categorias', component: CategoriasComponent },
  { path: 'acerca-de', component: AcercaDeComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
