
CREATE DATABASE petfy_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
/*
    utf8mb4 conjunto de caracteres que vamos a usar
    utf8mb4_unicode_ci usa reglas unicode, case insensitive 
*/

USE petfy_db;


/* TABLA USUARIOS */

CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    apellidos VARCHAR(50),
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    rol ENUM('CLIENTE', 'ADMIN') DEFAULT 'CLIENTE' NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;


/* TABLA CATEGORIAS */

CREATE TABLE categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    imagen_url VARCHAR(500),
    categoria_padre_id INT NULL,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_padre_id) REFERENCES categorias(id) ON DELETE SET NULL
) ENGINE=InnoDB;


/* TABLA PRODUCTOS */

CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    stock INT DEFAULT 0 CHECK (stock >= 0),
    imagen_url VARCHAR(500),
    categoria_id INT NOT NULL,
    tipo_animal ENUM('PERRO', 'GATO', 'ROEDOR', 'AVE', 'PEZ', 'REPTIL', 'OTRO', 'TODOS'),
    tamanio_mascota ENUM('MINI', 'PEQUEÑO', 'MEDIANO', 'GRANDE', 'GIGANTE', 'TODOS'),
    peso_producto_kg DECIMAL(8,2),
    duracion_estimada_dias INT,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE RESTRICT
) ENGINE=InnoDB;


/* TABLA: MASCOTAS */

CREATE TABLE mascotas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    tipo_animal ENUM('PERRO', 'GATO', 'ROEDOR', 'AVE', 'PEZ', 'REPTIL', 'OTRO') NOT NULL,
    raza VARCHAR(100),
    edad_anios INT,
    peso_kg DECIMAL(6,2),
    tamanio ENUM('MINI', 'PEQUEÑO', 'MEDIANO', 'GRANDE', 'GIGANTE'),
    nivel_actividad ENUM('BAJO', 'MEDIO', 'ALTO', 'MUY_ALTO'),
    alergias TEXT,
    fecha_nacimiento DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB;


/* TABLA  PEDIDOS */

CREATE TABLE pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE', 'PROCESANDO', 'ENVIADO', 'ENTREGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    total DECIMAL(10,2) NOT NULL CHECK (total >= 0),
    direccion_envio VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(10) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT
) ENGINE=InnoDB;


/* TABLA DETALLE_PEDIDO */

CREATE TABLE detalle_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10,2) NOT NULL CHECK (precio_unitario >= 0),
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE RESTRICT
) ENGINE=InnoDB;
