# Mundial 2026 - Sistema de Gestión

Aplicación de escritorio desarrollada en Java Swing para la gestión de información relacionada con el Mundial de Fútbol 2026.

El sistema permite administrar equipos, jugadores, partidos, usuarios, consultas, reportes y bitácora de acceso, utilizando una base de datos MySQL.

---

## Descripción general

Este proyecto fue desarrollado para la asignatura Bases de Datos I del programa de Ingeniería de Sistemas y Computación de la Universidad del Quindío.

La aplicación permite almacenar, consultar y gestionar información relacionada con el Mundial 2026, el cual se jugará en México, Estados Unidos y Canadá.

El sistema permite trabajar con información de:

- Equipos participantes.
- Jugadores.
- Partidos de fase de grupos.
- Estadios.
- Ciudades.
- Países anfitriones.
- Confederaciones.
- Usuarios del sistema.
- Bitácora de ingreso y salida.
- Consultas requeridas.
- Reportes requeridos.

La aplicación fue construida como una aplicación de escritorio sin frameworks, utilizando Java Swing para la interfaz gráfica y JDBC para la conexión con MySQL.

---

## Tecnologías utilizadas

- Java
- Java Swing
- JDBC
- MySQL
- Maven
- Git

---

## Estructura principal del proyecto

```text
mundial2026backend/
├── database/
│   ├── Mundial2026_full.sql
│   └── README_BD.md
├── src/
│   └── main/
│       └── java/
│           └── co/
│               └── mundial2026/
│                   ├── dao/
│                   ├── model/
│                   ├── security/
│                   ├── service/
│                   └── view/
├── pom.xml
└── README.md
```

---

## Módulos implementados

### Login y seguridad

El sistema cuenta con inicio de sesión mediante usuario y contraseña.

Las contraseñas se almacenan usando hash SHA-256.

Tipos de usuario manejados:

- Administrador
- Tradicional
- Esporadico

El administrador puede gestionar usuarios del sistema.

---

### Menú principal

El sistema cuenta con una ventana principal desde la cual se puede acceder a los diferentes módulos:

- Inicio
- Equipos
- Jugadores
- Partidos
- Consultas
- Reportes
- Usuarios
- Cerrar sesión

La interfaz fue personalizada con una estética visual inspirada en el Mundial 2026.

---

### Gestión de jugadores

Permite:

- Listar jugadores.
- Buscar jugadores.
- Crear jugadores.
- Editar jugadores.
- Eliminar jugadores.
- Asociar jugadores a equipos.
- Visualizar datos como:
  - Nombre.
  - Fecha de nacimiento.
  - Edad.
  - Posición.
  - Peso.
  - Estatura.
  - Valor de mercado.
  - Equipo.

---

### Gestión de equipos

Permite:

- Listar equipos.
- Buscar equipos.
- Crear equipos.
- Editar equipos.
- Eliminar equipos.
- Asociar equipos a confederaciones.
- Visualizar el valor total del equipo.

El valor total del equipo se actualiza con base en los jugadores registrados.

---

### Gestión de partidos

Permite:

- Listar partidos.
- Buscar partidos.
- Crear partidos.
- Editar partidos.
- Eliminar partidos.
- Registrar:
  - Equipo local.
  - Equipo visitante.
  - Estadio.
  - Grupo.
  - Fecha y hora.
  - Marcador.

Incluye validación para evitar registrar un partido con el mismo equipo como local y visitante.

---

### Consultas

El módulo de consultas está alineado con los requerimientos del proyecto.

Consultas implementadas:

- Jugador más costoso por confederación.
- Partidos por estadio seleccionado.
- Equipo más costoso por país anfitrión.
- Cantidad de jugadores menores de 21 años por equipo.

---

### Reportes

El módulo de reportes permite generar información dentro de la aplicación mediante tablas.

Reportes implementados:

- Bitácora de usuarios por rango de fecha y hora.
- Jugadores filtrados por peso, estatura y equipo.
- Valor total de jugadores por equipo según una confederación específica.
- Países/equipos que jugarán en cada país anfitrión.

Los reportes se muestran dentro de formularios de la aplicación.

---

### Bitácora

El sistema registra automáticamente:

- Fecha y hora de ingreso.
- Fecha y hora de salida.
- Usuario que inició sesión.

Esta información puede consultarse desde el módulo de reportes.

---

### Gestión de usuarios

Permite al administrador:

- Listar usuarios.
- Buscar usuarios.
- Crear nuevos usuarios.
- Editar usuarios.
- Eliminar usuarios, siempre que no tengan restricciones por bitácora o sesión activa.

---

## Requisitos previos

Antes de ejecutar el proyecto, asegúrese de tener instalado:

- Java JDK
- Maven
- MySQL
- Git

Verificar versiones:

```bash
java --version
mvn --version
mysql --version
git --version
```

---

## Configuración de la base de datos

El proyecto incluye un script completo de base de datos en:

```text
database/Mundial2026_full.sql
```

Este archivo contiene:

- Creación de la base de datos.
- Creación de tablas.
- Relaciones.
- Restricciones.
- Datos de prueba.
- Triggers.
- Procedimientos.
- Funciones.
- Vistas, si fueron exportadas en el respaldo.

---

## Importar la base de datos

Si MySQL está configurado en el puerto `3307`, ejecutar:

```bash
mysql --protocol=TCP -h 127.0.0.1 -P 3307 -u root -p < database/Mundial2026_full.sql
```

Si MySQL usa el puerto por defecto `3306`, ejecutar:

```bash
mysql -u root -p < database/Mundial2026_full.sql
```

Luego verificar desde MySQL:

```sql
USE Mundial2026;
SHOW TABLES;
```

---

## Configuración de conexión

La aplicación se conecta a la base de datos `Mundial2026`.

La configuración de conexión se encuentra en:

```text
src/main/java/co/mundial2026/security/DatabaseConnection.java
```

Allí se pueden ajustar los datos de conexión según el entorno local:

```text
host
puerto
usuario
contraseña
nombre de la base de datos
```

---

## Ejecutar el proyecto

Desde la raíz del proyecto:

```bash
cd mundial2026backend
mvn clean compile
mvn exec:java -Dexec.mainClass="co.mundial2026.Main"
```

---

## Usuario de prueba

Usuario administrador:

```text
Usuario: admin
Contraseña: admin123
Rol: Administrador
```

También se pueden crear nuevos usuarios desde el módulo de usuarios dentro de la aplicación.

---

## Flujo recomendado de prueba

1. Importar la base de datos.
2. Ejecutar la aplicación.
3. Iniciar sesión con el usuario administrador.
4. Revisar el menú principal.
5. Probar el módulo de jugadores.
6. Probar el módulo de equipos.
7. Probar el módulo de partidos.
8. Probar el módulo de consultas.
9. Probar el módulo de reportes.
10. Crear un nuevo usuario.
11. Cerrar sesión.
12. Iniciar sesión con el usuario creado.
13. Cerrar sesión nuevamente.
14. Revisar la bitácora desde reportes.

---

## Diseño de interfaz

La aplicación cuenta con una interfaz gráfica personalizada en Java Swing, con una estética visual inspirada en el Mundial 2026.

Características visuales:

- Tema oscuro.
- Botones redondeados.
- Colores dorados y rojos como acentos.
- Tablas personalizadas.
- Formularios estilizados.
- Scrolls personalizados.
- Alertas con tema visual adaptado a la aplicación.
- Menú principal con diseño visual moderno.

---

## Patrón de diseño utilizado

El proyecto utiliza el patrón DAO para separar la lógica de acceso a datos de la interfaz gráfica.

Las clases DAO se encuentran en:

```text
src/main/java/co/mundial2026/dao
```

Ejemplos:

- UsuarioDAO
- JugadorDAO
- EquipoDAO
- PartidoDAO
- BitacoraDAO
- ConsultaDAO
- ReporteDAO

---

## Organización del código

### dao

Contiene las clases encargadas de realizar operaciones con la base de datos.

### model

Contiene las clases que representan las entidades principales del sistema.

### security

Contiene clases relacionadas con autenticación, sesión y conexión con la base de datos.

### service

Contiene clases de apoyo para lógica del sistema.

### view

Contiene todas las interfaces gráficas desarrolladas con Java Swing.

---

## Comandos útiles de Maven

Compilar el proyecto:

```bash
mvn clean compile
```

Ejecutar el proyecto:

```bash
mvn exec:java -Dexec.mainClass="co.mundial2026.Main"
```

Limpiar archivos generados:

```bash
mvn clean
```

---

## Comandos útiles de Git

Ver estado del repositorio:

```bash
git status
```

Agregar cambios:

```bash
git add .
```

Crear commit:

```bash
git commit -m "Mensaje del commit"
```

Subir cambios:

```bash
git push origin main
```

---

## Estado del proyecto

El sistema cuenta con los módulos principales implementados y funcionales:

- Login
- Menú principal
- Jugadores
- Equipos
- Partidos
- Consultas
- Reportes
- Bitácora
- Usuarios

---

## Funcionalidades principales

- Autenticación de usuarios.
- Registro automático de ingreso y salida en bitácora.
- Gestión de jugadores.
- Gestión de equipos.
- Gestión de partidos.
- Gestión de usuarios.
- Consultas requeridas por el enunciado.
- Reportes requeridos por el enunciado.
- Interfaz gráfica personalizada.
- Conexión con MySQL mediante JDBC.

---

## Notas sobre seguridad

El sistema utiliza hash SHA-256 para almacenar las contraseñas.

No se almacenan contraseñas en texto plano.

Además, se maneja una sesión activa mediante una clase de administración de sesión.

---

## Consideraciones importantes

- La aplicación debe ejecutarse con la base de datos previamente importada.
- Es necesario revisar que los datos de conexión coincidan con el entorno local.
- Si el puerto de MySQL es diferente, debe ajustarse en la clase `DatabaseConnection`.
- Si se elimina un usuario con registros de bitácora, la base de datos puede impedir la eliminación por integridad referencial.
- Si se elimina un equipo con jugadores o partidos asociados, la base de datos puede impedir la eliminación por integridad referencial.

---

## Autores

Proyecto desarrollado para la asignatura Bases de Datos I.

Programa de Ingeniería de Sistemas y Computación.

Universidad del Quindío.

Juan José Barrero Jaramillo
Nicolas Loaiza

---

## Notas finales

Este proyecto fue desarrollado como una aplicación de escritorio sin frameworks, aplicando conexión JDBC, patrón DAO, manejo de sesiones, validaciones básicas, consultas SQL y reportes sobre una base de datos relacional en MySQL.

La finalidad del sistema es permitir la gestión de información relacionada con el Mundial 2026, cumpliendo con los requerimientos principales del proyecto final de Bases de Datos I.
