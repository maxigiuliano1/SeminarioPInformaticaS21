# Seminario de Practica de Informática – S21

Este repositorio contiene un proyecto de escritorio desarrollada como entrega parcial de la asignatura Seminario de Practica de Informática (S21). El mismo sera un **sistema de gestión de inventario automatizado**.

---

## 📌 Descripción

Software de escritorio para llevar el registro y control de inventario. La aplicación permite hasta el momento:
- Registrar nuevos productos y editar datos existentes.
- Registrar y editar proveedores.
- Importar datos desde archivos Excel para facilitar la carga masiva.
- Actualizar precio de producto (costo y venta) desde un archivo excel
- Registro de movimiento de inventario (ENTRADA y SALIDA de productos)
- Módulos en desarrollo (registro de usuarios es opcional).
- Incorporación de interfaz gráfica con librería Swing

---

## 🛠️ Tecnologías y dependencias

- **Lenguaje**: Java 21
- **Build Tool**: Apache Maven
- **Dependencias declaradas en `pom.xml`**:
    - **JDBC**: para la conexión y manipulación de bases de datos (MySQL)
    - **Apache POI**: para leer archivos Excel y procesar datos estructurados
