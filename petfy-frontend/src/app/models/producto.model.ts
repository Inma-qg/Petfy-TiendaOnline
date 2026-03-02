export interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  imagenUrl?: string;
  categoria?: {
    id: number;
    nombre: string;
  };
  tipoAnimal?: string;
  activo: boolean;
}
