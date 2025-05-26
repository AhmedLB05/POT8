# 🛍️ FernanShop - POT6  
**Práctica Obligatoria Tema 6**  
Desarrollado por: **Ahmed Lhaouchi Briki** y **Marcos Lara Cano**

![LogoTienda](https://github.com/user-attachments/assets/85028b68-ae5f-441d-998c-aad31c6d186b)

---

## 📑 Índice

- [📌 Presentación](#presentación)  
- [⚙️ Instalación](#instalación)  
- [🚀 Cómo iniciar](#cómo-iniciar)  
- [🖥️ Ejecución del programa](#ejecución-del-programa)  
  - [🔸 Opción 1: Catálogo de productos](#🔸-opción-1-catálogo-de-productos)  
  - [🔸 Opción 2: Registro de usuario](#🔸-opción-2-registro-de-usuario)  
  - [🔸 Opción 3: Iniciar sesión](#🔸-opción-3-iniciar-sesión)  
- [🛠️ Funcionalidades por tipo de usuario](#🛠️-funcionalidades-por-tipo-de-usuario)  
  - [👑 Administrador](#👑-administrador)  
  - [👷 Trabajador](#👷-trabajador)  
  - [🛒 Cliente](#🛒-cliente)  
- [📬 Notificaciones y comunicación](#📬-notificaciones-y-comunicación)  
- [💾 Persistencia y configuración](#💾-persistencia-y-configuración)  
- [🔒 Seguridad](#🔒-seguridad)

---

## 📌 Presentación

**FernanShop** es una aplicación de consola que simula el funcionamiento de una tienda virtual, desarrollada como parte de la **Práctica Obligatoria del Tema 6** de la asignatura de Programación.

Incluye funcionalidades como:

- Gestión de productos, clientes, trabajadores y pedidos.
- Persistencia de datos mediante ficheros.
- Envío de notificaciones por correo electrónico y Telegram.
- Interfaz de usuario completamente por consola.

---

## ⚙️ Instalación

1. Instala **JDK 23** si no lo tienes.  
   👉 [Descargar JDK 23 (Windows)](https://download.oracle.com/java/24/latest/jdk-24_windows-x64_bin.exe)

2. Ejecuta el instalador y sigue las instrucciones para completar la instalación.

---

## 🚀 Cómo iniciar

1. Descarga el archivo `.zip` desde el repositorio de GitHub.  
2. Descomprime el archivo y accede a la carpeta `out/artifacts/POT6_jar`.  
3. Ejecuta el archivo `EJECUTABLE.bat` haciendo doble clic para iniciar la aplicación.

![Ruta de inicio](https://github.com/user-attachments/assets/8da1ca24-d62c-4e6e-b9b1-b26101d0fea4)

---

## 🖥️ Ejecución del programa

Al iniciar por primera vez, se te preguntará si deseas cargar datos de prueba. Esto solo ocurre en la primera ejecución.

### Menú principal

Desde el menú principal puedes elegir entre tres opciones:

![Menú principal](https://github.com/user-attachments/assets/eb6b49bb-4521-4bd9-8ba8-75fcfdf1d56b)

---

### 🔸 Opción 1: Catálogo de productos

Visualiza el listado completo de productos disponibles, incluyendo detalles como nombre, precio, categoría y stock. Puedes navegar por páginas o volver al menú principal.

---

### 🔸 Opción 2: Registro de usuario

Permite registrar un nuevo usuario con verificación por correo electrónico. Se enviará un código de confirmación para validar el proceso.

---

### 🔸 Opción 3: Iniciar sesión

Accede al sistema como **cliente**, **trabajador** o **administrador**, con funcionalidades específicas para cada rol.

---

## 🛠️ Funcionalidades por tipo de usuario

---

### 👑 Administrador

Acceso completo al sistema, incluyendo gestión y supervisión de todos los elementos.

- **Catálogo de productos**: Visualización avanzada con filtros.  
- **Modificar producto**: Edita cualquier detalle de un producto.  
- **Clientes y pedidos**: Consulta y gestiona la información.  
- **Estadísticas**: Visualiza métricas globales del sistema.  
- **Trabajadores**: Alta, baja y gestión de empleados.  
- **Asignación de pedidos**: Envía notificaciones por email y Telegram.  
- **Configuración**: Visualiza archivos `.properties` del sistema.  
- **Resumen por correo**: Genera un Excel con pedidos y envíalo.  
- **Copias de seguridad**: Exporta e importa el estado del sistema.

---

### 👷 Trabajador

Gestión enfocada en productos y pedidos asignados.

- **Pedidos pendientes**: Lista de pedidos por procesar.  
- **Modificar pedidos**: Estado y comentarios.  
- **Catálogo de productos**: Visualización y edición.  
- **Historial**: Consultar pedidos procesados.  
- **Perfil personal**: Consulta y edición de datos propios.

---

### 🛒 Cliente

Opciones básicas para comprar y gestionar sus pedidos.

- **Ver productos**: Consulta el catálogo completo.  
- **Realizar pedido**: Selecciona productos y completa el proceso de compra.  
- **Historial de pedidos**: Consulta detalles de compras anteriores.  
- **Perfil**: Edita tus datos personales.

---

## 📬 Notificaciones y comunicación

- 📧 **Correo electrónico**: Confirmaciones, verificaciones, resumen de pedidos.  
- 📲 **Telegram**: Notificación de asignaciones a trabajadores.

---

## 💾 Persistencia y configuración

- Todos los datos se almacenan mediante ficheros.  
- El archivo `.properties` permite configurar parámetros clave como credenciales de correo, tokens de bots, y otras opciones del sistema.

---

## 🔒 Seguridad

- Sistema de validación para clientes y trabajadores.  
- Restricciones de acceso basadas en rol.  
- Envío de códigos únicos para registro y acciones importantes.

---

¿Tienes sugerencias o necesitas ayuda? ¡Gracias por usar **FernanShop**!
