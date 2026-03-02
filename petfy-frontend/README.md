# PETFY Frontend - Angular

Tienda online de productos para mascotas desarrollada con Angular 17.

## 🚀 Instalación Rápida

### Requisitos previos
- Node.js (v18 o superior)
- npm

### Pasos de instalación

1. **Instalar dependencias**:
```bash
npm install
```

2. **Iniciar servidor de desarrollo**:
```bash
npm start
```

La aplicación se abrirá en `http://localhost:4200`

## ⚙️ Configuración del Backend

Asegúrate de que el backend Spring Boot esté corriendo en `http://localhost:8080`

Si tu backend está en otra URL, edita los servicios en:
- `src/app/services/producto.service.ts`
- `src/app/services/categoria.service.ts`

Y cambia la línea:
```typescript
private apiUrl = 'http://localhost:8080/productos';
```

## 📦 Build para Producción

```bash
npm run build
```

Los archivos compilados estarán en `dist/petfy-frontend/`

## 🛠️ Tecnologías Utilizadas

- **Angular 17** - Framework principal
- **TypeScript** - Lenguaje de programación
- **Bootstrap 5** - Framework CSS
- **Bootstrap Icons** - Iconografía
- **RxJS** - Programación reactiva

## 📁 Estructura del Proyecto

```
src/app/
├── components/          # Componentes de la aplicación
│   ├── navbar/         # Barra de navegación
│   ├── footer/         # Pie de página
│   ├── home/           # Página principal
│   ├── productos/      # Listado de productos
│   ├── producto-detalle/  # Detalle de producto
│   ├── categorias/     # Listado de categorías
│   └── acerca-de/      # Página "Sobre nosotros"
├── services/           # Servicios para API
│   ├── producto.service.ts
│   └── categoria.service.ts
└── models/             # Interfaces TypeScript
    ├── producto.model.ts
    └── categoria.model.ts
```

## 🎯 Funcionalidades Implementadas

✅ Listado de productos desde la API  
✅ Búsqueda de productos por nombre  
✅ Vista detallada de cada producto  
✅ Listado de categorías  
✅ Navegación entre páginas  
✅ Diseño responsive  
✅ Manejo de estados de carga  
✅ Manejo de errores  

## 👥 Equipo de Desarrollo

- Aida Reyes Galván
- Alejandro Pineda Cladera
- Inmaculada Quilón García

**Tutor:** César Carrión  
**Centro:** UNIR FP  
**Proyecto:** TFG - Desarrollo de Aplicaciones Web

## 📄 Licencia

Proyecto académico - TFG 2026
