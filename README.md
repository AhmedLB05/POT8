# POT6  
Práctica Obligatoria Tema 6 by Ahmed Lhaouchi Briki y Marcos Lara Cano

![LogoTienda](https://github.com/user-attachments/assets/85028b68-ae5f-441d-998c-aad31c6d186b)

## Índice

- [Presentación](#presentación)  
- [Instalación](#instalación)  
- [Iniciar el programa](#iniciar)  
- [Ejecución del programa](#ejecución)  

---

## Presentación

Este proyecto corresponde a la Práctica Obligatoria del Tema 6 de la asignatura de Programación. Consiste en el desarrollo de **FernanShop**, una aplicación que simula el funcionamiento de una tienda virtual, permitiendo la **gestión de pedidos, productos, clientes y trabajadores**. Su propósito es aprender a estructurar correctamente un programa que implemente una arquitectura robusta, uso de ficheros, persistencia, interfaces de usuario por consola, y envío de notificaciones vía email y Telegram.

---

## Instalación

Para poder ejecutar el programa, debemos instalar el **JDK 23**.  
[Haz clic aquí para descargarlo directamente en tu ordenador](https://download.oracle.com/java/24/latest/jdk-24_windows-x64_bin.exe).  
El programa comenzará a descargarse automáticamente y deberás ejecutar el instalador para completar la instalación.

---

## Iniciar

Descargamos el archivo `.zip` desde el repositorio de GitHub.  
Una vez descargado, lo descomprimimos y abrimos la carpeta que se genera.

![Captura de pantalla 2025-04-20 224253](https://github.com/user-attachments/assets/aeffd688-47fc-4101-9705-918bd1f00c2e)

![Captura de pantalla 2025-04-20 225114](https://github.com/user-attachments/assets/aa016b8c-f765-43f6-9d37-9619095a36bf)

Dentro de la carpeta `out/artifacts/POT6_jar`, encontramos el ejecutable `EJECUTABLE.bat`. Haciendo doble clic, se inicia la aplicación.

![image](https://github.com/user-attachments/assets/8da1ca24-d62c-4e6e-b9b1-b26101d0fea4)

---

## Ejecución

Al iniciar por primera vez, el programa nos preguntará si queremos añadir datos de prueba.

![image](https://github.com/user-attachments/assets/18242d42-d0e7-4284-8e0c-95b8abc513cc)  
![image](https://github.com/user-attachments/assets/7b304875-9940-4167-ba6d-afbfc7cc3ff7)  
![image](https://github.com/user-attachments/assets/d5b33b47-e91d-4a77-9d06-b2239a1af805)  
![image](https://github.com/user-attachments/assets/1a6738f2-57d1-4d3f-b160-9484189d003b)

Este proceso solo ocurre en la primera ejecución. En futuras ejecuciones, el sistema informará de los datos ya guardados.

![image](https://github.com/user-attachments/assets/1fd48801-e638-4bab-a164-2b2d4d83ed94)

En el menú principal encontramos tres opciones principales:

![image](https://github.com/user-attachments/assets/eb6b49bb-4521-4bd9-8ba8-75fcfdf1d56b)

---

### Opción 1: Catálogo de productos

Se muestra una lista de productos disponibles para la venta, donde se puede ver el nombre, precio, categoría, stock y otros detalles. El catálogo está dividido en páginas, y puedes presionar **ENTER** para cargar más productos o escribir cualquier otra cosa para volver al menú principal.

![image](https://github.com/user-attachments/assets/6c5478e1-24a0-4bbe-908a-aca94a403b0e)

---

### Opción 2: Registro de usuario

Permite registrar un nuevo usuario que será validado mediante un correo electrónico. Se ingresa el nombre, correo y contraseña. El sistema enviará un código de verificación al correo para completar el proceso de registro.

![Captura de pantalla 2025-03-23 132630](https://github.com/user-attachments/assets/9e2a2740-9d8b-485e-814a-9d26a485388a)

Correo recibido con el código de verificación:

![4c92bf7c-288f-4e27-9356-00f6ea236212](https://github.com/user-attachments/assets/a0d23f78-47cb-44fc-91d4-ba3c5a34140f)

---

### Opción 3: Iniciar sesión

Permite iniciar sesión como **administrador**, **trabajador** o **cliente**, dependiendo de los permisos asignados al usuario.

![image](https://github.com/user-attachments/assets/a83e15b2-dab3-466b-bca7-e3da24455cf6)

---

## Administrador

Como **administrador**, tendrás acceso completo a todas las funcionalidades del sistema, permitiéndote gestionar los productos, clientes, pedidos y trabajadores.

### Opción 1: Catálogo de productos

Permite visualizar y buscar productos en el sistema por distintos criterios como nombre, categoría, stock y precio.

![image](https://github.com/user-attachments/assets/e3ab247a-7208-464f-9644-1bd13a7207a2)

### Opción 2: Modificar producto

Te permite modificar cualquier campo de un producto a partir de su ID (por ejemplo, nombre, precio, descripción, stock, etc.).

![image](https://github.com/user-attachments/assets/0723f8e5-4b82-465d-8042-7ee0f5a87133)

### Opción 3: Ver clientes

Consulta de todos los clientes registrados en la tienda, donde puedes ver sus datos personales y estado de cuenta.

![a9ec6a79-5d1c-4a78-9d72-e8bf5008e67b](https://github.com/user-attachments/assets/387ce823-066a-4cf1-a027-72d7e85852d6)

### Opción 4: Ver pedidos

Consulta los pedidos realizados, su estado y detalles relacionados con la compra.

![3108bd9f-3951-445b-ac8e-b126f470b558](https://github.com/user-attachments/assets/080d8ef9-b85f-429c-9e46-a9df646ed764)

### Opción 5: Ver trabajadores

Consulta de todos los trabajadores registrados en el sistema, incluyendo sus detalles y su estado de empleo.

![b912b662-de8b-4dd2-9441-9bced733150e](https://github.com/user-attachments/assets/5c205ac3-2931-49d1-b82c-5bd868a824be)

### Opción 6: Ver estadísticas

Accede a un resumen de estadísticas generales del sistema, como el número de pedidos realizados, productos vendidos y estadísticas de clientes.

![7af1b7c6-5436-4e80-944f-1c00151cf233](https://github.com/user-attachments/assets/ad64abca-ed5a-4fbc-9935-6a5e7e1149fe)

### Opción 7: Modificar estado/comentario pedido

Puedes modificar el estado de un pedido o añadir un comentario relacionado. También notificas a los clientes por correo electrónico sobre cualquier cambio.

![1dd7912b-fd70-4d1e-a2f0-6edadf4f4581](https://github.com/user-attachments/assets/cedeb9da-336d-458c-91d3-8966fd94ff2c)

### Opción 8: Alta de trabajador

Permite agregar nuevos trabajadores al sistema con sus datos correspondientes.

![e75168d2-226a-417f-b9d1-3f4c2cf223e2](https://github.com/user-attachments/assets/b586c61f-3c06-4878-a399-55f33f8c50ce)

### Opción 9: Baja de trabajador

Permite dar de baja a un trabajador del sistema, removiendo sus datos del sistema.

![0a7e3ee5-3e7c-4013-b335-5ca0fe520b8a](https://github.com/user-attachments/assets/3d076dd3-8c5a-41f1-a823-25cff91160f2)

### Opción 10: Asignar pedidos

Permite asignar un pedido a un trabajador específico, con notificación a través de correo y Telegram.

![972b945b-5e6b-4c05-a829-473755af3ece](https://github.com/user-attachments/assets/43ae847f-9512-49f8-b494-cd237f6d5f46)  
![c73f8d9d-6c49-4704-aff4-5b23540ebd73](https://github.com/user-attachments/assets/cbe096cd-ccdf-4f46-8b17-e448c5fbeb0c)  
![b39cc548-7111-4020-a520-4d79f1ec4b79](https://github.com/user-attachments/assets/bf686952-90b4-421c-8254-ce2f237832fe)

### Opción 11: Ver configuración

Muestra los archivos de configuración del sistema, como el archivo `.properties`, donde se almacenan parámetros configurables como correo y ajustes de base de datos.

![image](https://github.com/user-attachments/assets/34e3da81-8122-4266-a8b7-6b4fdd97fec3)

### Opción 12: Enviar resumen por correo

Permite enviar un resumen de todos los pedidos registrados en el sistema a través de correo electrónico en formato Excel.

![image](https://github.com/user-attachments/assets/9d113266-ed42-4fcd-9870-b43cb2a9ec42)  
![image](https://github.com/user-attachments/assets/54983d99-0214-45d7-b39d-4899f3c98cbe)

### Opción 13: Copia de seguridad

Permite crear y restaurar copias de seguridad, con la posibilidad de elegir la ruta de almacenamiento.

![image](https://github.com/user-attachments/assets/2a08fdfa-d1b0-488b-b0f1-9a620fcc2c81)  
![image](https://github.com/user-attachments/assets/d1644b71-7fdf-4475-822b-df3574bcd69a)

---

## Trabajador

El trabajador tiene acceso solo a las opciones relacionadas con la gestión de pedidos y productos.

### Opción 1: Consultar pedidos pendientes

Muestra los pedidos pendientes de ser procesados o enviados.

![5b09f259-5775-4380-8423-558c58691a78](https://github.com/user-attachments/assets/5e9f90b6-f4d5-426c-a248-e72fc3d311e0)

### Opción 2: Modificar pedido

Permite cambiar detalles de un pedido como el estado y añadir comentarios.

![1dd7912b-fd70-4d1e-a2f0-6edadf4f4581](https://github.com/user-attachments/assets/cedeb9da-336d-458c-91d3-8966fd94ff2c)

### Opción 3: Ver catálogo

Accede al catálogo de productos de la tienda, igual que en el menú administrador.

### Opción 4: Editar producto

Si tiene permisos, también podrá modificar productos del catálogo.

### Opción 5: Ver historial

Consulta de los pedidos terminados y su estado final.

![704ced2b-fb36-491b-abf7-812818cb0e70](https://github.com/user-attachments/assets/7a63f614-9e50-4308-b713-0363d1bd4c02)

### Opción 6: Ver perfil

Permite ver los datos personales del trabajador.

![4ab0d386-99f4-437b-92b2-1cc1a52ac7da](https://github.com/user-attachments/assets/c58464b4-dbd5-4a9d-8777-5cf0e8e05374)

### Opción 7: Editar perfil

El trabajador podrá modificar su perfil con cambios de datos personales.

![f99b1f4c-26fe-423a-b802-a005484b2236](https://github.com/user-attachments/assets/f5a9477f-62ac-4762-9ec5-a3686d119a87)

---

## Cliente

El cliente puede realizar compras, ver su historial de pedidos y actualizar sus datos personales.

### Opción 1: Ver productos

Permite al cliente consultar los productos disponibles en la tienda.

![Captura de pantalla 2025-03-23 133017](https://github.com/user-attachments/assets/b7a62828-db7d-41d4-a6a1-2362658e11dc)

### Opción 2: Realizar pedido

Permite al cliente realizar un pedido seleccionando productos del catálogo y pasando por el proceso de compra.

![Captura de pantalla 2025-03-23 150004](https://github.com/user-attachments/assets/a66c2865-32b7-45b0-a899-580c322b0f8e)  
![Captura de pantalla 2025-03-23 150201](https://github.com/user-attachments/assets/ae50f4e2-5a81-423f-973b-60227a12a9c3)

### Opción 3: Ver historial

Muestra los pedidos realizados por el cliente en el pasado y su estado.

![74d4415c-69de-4b26-a72e-62ea2f312921](https://github.com/user-attachments/assets/efbd2ce6-a3ec-46f9-af3e-6e0ca1112b82)

### Opción 4: Datos personales

Muestra los datos personales del cliente.

![a7fc8b36-db61-41e8-8d91-4ddedd31c65f](https://github.com/user-attachments/assets/d92daeda-38ea-4f7f-b79d-c2ab27fa4302)

### Opción 5: Editar perfil

Permite al cliente modificar sus datos personales.

![874e9033-0017-4c9c-b7e6-b9d6415383b0](https://github.com/user-attachments/assets/ef2bbe5c-878d-4f93-8e4e-855e754d11b1)

## Persistencia

### Persistencia de datos

El sistema mantiene los datos de los usuarios, productos, pedidos y trabajadores a través de ficheros, garantizando que la información persista incluso después de cerrar la aplicación.

![130fd595-8227-4284-a150-ae012c996f1c](https://github.com/user-attachments/assets/51356e33-62a3-4504-9e6b-0942edeb6529)

- **Generación de logs**: Se registran las acciones importantes realizadas en el sistema para un seguimiento detallado.

![54a6f856-ae58-44ca-93f7-f28288de84ea](https://github.com/user-attachments/assets/a5adc2ef-33cf-4bb4-a6d6-5eb0ea8ed613)

- **Archivo de propiedades**: Configura parámetros como el correo de notificación y ajustes de la base de datos.

![ac5b4853-5c0a-47f3-b1af-3f2bd68ec221](https://github.com/user-attachments/assets/7b01436c-ff4f-4336-bc29-0d3665b91b8b)

- **Confirmación de pedidos por correo**: Cuando un pedido es realizado, se envía un correo al cliente con los detalles en formato pdf.

![b9b680d1-6262-4858-a623-08c58c925f71](https://github.com/user-attachments/assets/f461e078-b60c-4fc9-ac7f-e8874ff9fe55)  

- **Se muestra la hora de ultimo inicio de sesión de un usuario**: Cuando un usuario inicia sesión en la cabecera de su menú se le muestra fecha y hora de su ultimo inicio de sesión:

![image](https://github.com/user-attachments/assets/412b8e61-3d12-4713-aae0-0b56eae25cda)

## Nuevas implementaciones
---

### Guardado de datos en una Base de Datos

El sistema ahora incluye la funcionalidad de guardar los datos del programa en una base de datos, incluye tanto configuraciones del programa como datos de clientes, trabajadores, administradores, productos, pedidos y un largo etcétera.

Distribuimos nuestra base de datos en 7 tablas:

![4749f521-708e-4f6a-a536-1ed39b0749b5](https://github.com/user-attachments/assets/f2c9fecc-7667-4a37-a7e0-f96aaa96a20c)

- **Trabajador**: Con atributos como id, nombre, contraseña, email y movil, guardamos los datos de todos nuestros trabajadores.
- **Admin**: Con atributos como id, nombre, contraseña y email, guardamos los datos de todos nuestros administradores. Aunque actualmente solo tenemos un solo admin.
- **Pedido**: Con atributos como id, fecha del pedido, fecha de entrega estimada, estado del pedido, comentario que agrega nuestro trabajador encargado del pedido, id del cliente que realizó el pedido y id del trabajador que lo gestiona, guardamos los datos de todos nuestros pedidos.
- **Cliente**: Con atributos como id, email, contraseña, nombre, localidad, provincia, dirección y movil, guardamos los datos de todos nuestros clientes.
- **Pedido_Productos**: Con atributos como id del pedido y id del producto, establecemos la conexion entre los productos que se hayan en un pedido.
- **Producto**: Con atributos como id, marca, modelo, descripción, precio y relevancia, guardamos los datos de todos nuestros productos.
- **Carro**: Con atributos como id del cliente y id del producto, establecemos conexión entre los productos que se hayan en el carro de un cliente.




