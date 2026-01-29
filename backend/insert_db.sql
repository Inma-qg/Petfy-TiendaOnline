/*DATOS DE PRUEBA*/

USE petfy_db;

/* USUARIOS */

-- Password: "password123" (en producción usar bcrypt)
INSERT INTO usuarios (nombre, apellidos, email, password, telefono, rol) VALUES
('Admin', 'Sistema', 'admin@petshop.com', '$2a$10$xZ8kxqCjGtQB5n8aP9t0QuPvH7wI0bZ4Yx5QYkJH5h8gM7Qe5kL8u', '600123456', 'ADMIN'),
('María', 'García López', 'maria.garcia@email.com', '$2a$10$xZ8kxqCjGtQB5n8aP9t0QuPvH7wI0bZ4Yx5QYkJH5h8gM7Qe5kL8u', '611234567', 'CLIENTE'),
('Carlos', 'Rodríguez Pérez', 'carlos.rodriguez@email.com', '$2a$10$xZ8kxqCjGtQB5n8aP9t0QuPvH7wI0bZ4Yx5QYkJH5h8gM7Qe5kL8u', '622345678', 'CLIENTE');

/* CATEGORÍAS */

INSERT INTO categorias (nombre, descripcion, imagen_url) VALUES
('Alimentación', 'Comida y piensos para todas las mascotas', 'https://images.unsplash.com/photo-1589924691995-400dc9ecc119'),
('Accesorios', 'Collares, correas, arneses y más', 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1');


INSERT INTO categorias (nombre, descripcion, categoria_padre_id) VALUES
('Pienso para Perros', 'Alimento seco completo y balanceado', 1),
('Pienso para Gatos', 'Nutrición especializada felina', 1),
('Collares y Correas', 'Accesorios para paseo', 2),
('Pelotas y Mordedores', 'Juguetes para actividad física', 3),
('Juguetes Interactivos', 'Estimulación mental', 3);

/* PRODUCTOS - perros */
INSERT INTO productos (nombre, descripcion, precio, stock, imagen_url, categoria_id, tipo_animal, tamanio_mascota, peso_producto_kg, duracion_estimada_dias) VALUES
('Hill\'s Science Plan Adult Small', 'Nutrición precisa para perros pequeños. Antioxidantes clínicamente probados.', 45.99, 40, 'https://images.unsplash.com/photo-1587300003388-59208cc962cb', 6, 'PERRO', 'PEQUEÑO', 7.0, 40),
('Acana Heritage Adult Dog', 'Pienso biológicamente apropiado con 60% de ingredientes de origen animal.', 69.99, 25, 'https://images.unsplash.com/photo-1568640347023-a616a30bc3bd', 6, 'PERRO', 'TODOS', 11.4, 38);


/* PRODUCTOS - gatos*/
INSERT INTO productos (nombre, descripcion, precio, stock, imagen_url, categoria_id, tipo_animal, tamanio_mascota, peso_producto_kg, duracion_estimada_dias) VALUES
('Royal Canin Indoor Adult', 'Especial para gatos de interior. Control de bolas de pelo y peso ideal.', 42.99, 35, 'https://images.unsplash.com/photo-1574158622682-e40e69881006', 7, 'GATO', 'TODOS', 10.0, 50),
('Acana Pacifica Cat', 'Receta con pescado fresco y completo. Sin cereales. 75% de ingredientes animales.', 65.99, 20, 'https://images.unsplash.com/photo-1573865526739-10c1dd482abd', 7, 'GATO', 'TODOS', 5.4, 35);


/* PRODUCTOS - comida húmeda*/
INSERT INTO productos (nombre, descripcion, precio, stock, imagen_url, categoria_id, tipo_animal, tamanio_mascota, peso_producto_kg, duracion_estimada_dias) VALUES
('Hill\'s Science Plan Adult Cordero - 12x370g', 'Nutrición completa y equilibrada. Alta digestibilidad.', 32.99, 35, 'https://images.unsplash.com/photo-1587300003388-59208cc962cb', 1, 'PERRO', 'TODOS', 4.44, 12);

/* PRODUCTOS - snacks*/
INSERT INTO productos (nombre, descripcion, precio, stock, imagen_url, categoria_id, tipo_animal, tamanio_mascota, peso_producto_kg, duracion_estimada_dias) VALUES
('Tiras de Salmón Deshidratado', 'Snack natural y saludable. Omega-3 para piel y pelo brillante. 100g', 8.99, 55, 'https://images.unsplash.com/photo-1560743173-567a3b5658b1', 1, 'PERRO', 'TODOS', 0.1, 20);



/* MASCOTAS */

INSERT INTO mascotas (usuario_id, nombre, tipo_animal, raza, edad_anios, peso_kg, tamanio, nivel_actividad, alergias, fecha_nacimiento) VALUES
(1, 'Toby', 'PERRO', 'Golden Retriever', 4, 32.5, 'GRANDE', 'ALTO', 'Ninguna', '2020-03-15'),
(2, 'Misi', 'GATO', 'Siamés', 2, 4.2, 'PEQUEÑO', 'MEDIO', 'Ninguna', '2022-06-20'),
(2, 'Rocked', 'PERRO', 'Chihuahua', 1, 2.8, 'MINI', 'MUY_ALTO', 'Ninguna', '2023-11-22');



/* PEDIDOS */

INSERT INTO pedidos (usuario_id, fecha_pedido, estado, total, direccion_envio, ciudad, codigo_postal) VALUES
(2, '2025-01-15 10:30:00', 'ENTREGADO', 108.97, 'Calle Mayor 23, 3º B', 'Madrid', '28013'),
(3, '2025-01-20 15:45:00', 'ENVIADO', 87.98, 'Avenida Libertad 56', 'Barcelona', '08001'),
(2, '2025-01-27 14:00:00', 'PENDIENTE', 156.94, 'Calle Mayor 23, 3º B', 'Madrid', '28013');


/* DETALLE_PEDIDO*/

/* Pedido 1 (María - Entregado)*/

INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 45.99, 45.99), 
(1, 5, 1, 32.99, 32.99); 
/* Pedido 2 (Carlos - Enviado)*/
INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(2, 3, 1, 42.99, 42.99), 
(2, 4, 1, 65.99, 65.99); 


