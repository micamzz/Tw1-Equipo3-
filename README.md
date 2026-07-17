# Basket UNLAM

![Java](https://img.shields.io/badge/Java-11-orange?style=for-the-badge\&logo=openjdk)
![Spring MVC](https://img.shields.io/badge/Spring%20MVC-Framework-6DB33F?style=for-the-badge\&logo=spring)
![Spring](https://img.shields.io/badge/Spring-Core-6DB33F?style=for-the-badge\&logo=spring)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge\&logo=hibernate)
![JPA](https://img.shields.io/badge/JPA-Persistence-59666C?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge\&logo=mysql\&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge\&logo=junit5)
![Mockito](https://img.shields.io/badge/Mockito-Testing-78A641?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge\&logo=apachemaven)
![Git](https://img.shields.io/badge/Git-Version%20Control-F05032?style=for-the-badge\&logo=git\&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge\&logo=github)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge\&logo=bootstrap)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge\&logo=html5\&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge\&logo=css3)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge\&logo=javascript\&logoColor=black)

**Basket UNLAM** es una aplicación web desarrollada como **Trabajo Práctico Grupal** para la materia **Taller Web I** de
la **Universidad Nacional de La Matanza (UNLaM)**.

El proyecto recrea una plataforma de tipo **Fantasy NBA**, donde cada usuario administra su propio equipo utilizando
jugadores reales de la NBA, respetando un presupuesto determinado y compitiendo en un torneo mediante un sistema de
puntajes basado en estadísticas reales.

El objetivo principal fue aplicar los conocimientos adquiridos durante la cursada utilizando **Java**, **Spring MVC**, *
*Hibernate**, **JPA** y una arquitectura en capas, implementando buenas prácticas de desarrollo, persistencia de datos,
testing y trabajo colaborativo.


### Objetivos
---

* Aplicar el patrón MVC.
* Implementar una arquitectura en capas.
* Utilizar Hibernate como framework de persistencia.
* Modelar relaciones entre entidades mediante JPA.
* Implementar reglas de negocio desacopladas.
* Desarrollar una aplicación siguiendo principios de orientación a objetos.
* Gestionar el proyecto utilizando Git y GitHub.
* Realizar pruebas unitarias mediante JUnit y Mockito.

### Funcionalidades
---

#### Administrador

El administrador posee control total sobre la plataforma.

Entre sus funcionalidades se encuentran:

* Inicio de sesión.
* Administración de usuarios administradores.
* Creación de equipos oficiales de la NBA.
* Modificación de equipos NBA.
* Administración de jugadores.
* Asignación de jugadores a los equipos oficiales.
* Creación de temporadas.
* Creación de torneos.
* Asociación de equipos a un torneo.
* Administración general del sistema.

#### Usuario

Cada usuario registrado puede:

* Registrarse e iniciar sesión.
* Crear un equipo Fantasy.
* Participar automáticamente del torneo vigente.
* Seleccionar jugadores para su plantilla.
* Comprar y vender jugadores.
* Administrar el presupuesto disponible.
* Asignar un Capitán.
* Asignar un Sexto Hombre.
* Consultar estadísticas individuales.
* Visualizar el valor de mercado de cada jugador.
* Consultar el puntaje total del equipo.
* Visualizar la tabla de posiciones.
* Modificar su equipo respetando todas las reglas de negocio.

## Arquitectura

El proyecto fue desarrollado siguiendo una arquitectura MVC con separación en capas.

Cada componente posee una única responsabilidad.

```text
Usuario
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
Base de Datos
```

# Estructura del Proyecto

```text
src
│
├── main
│   ├── java
│   │
│   └── com.tallerwebi
│       ├── config
│       ├── dominio
│       ├── infraestructura
│       ├── presentacion
│       └── MyServletInitializer
│
│   ├── resources
│   └── webapp
│
└── test
    └── java
        └── com.tallerwebi
            ├── dominio
            ├── infraestructura
            ├── integracion
            ├── presentacion
            └── punta_a_punta
```

---

# Testing

El proyecto incorpora distintos niveles de pruebas automatizadas.

#### Pruebas Unitarias

* Reglas de negocio.
* Servicios.
* Validaciones.
* Cálculo de puntajes.
* Gestión de presupuesto.

#### Pruebas de Persistencia

* Repositorios.
* Consultas.
* Relaciones entre entidades.

##### Pruebas de Presentación

* Controladores MVC.
* Flujo entre vistas y servicios.

## Capturas de Pantalla

<p align="center">
  <img src="docs/landing.png" alt="Landing Page" width="48%">
  <img src="docs/home-admin.png" alt="Panel de Administración" width="48%">
</p>

<p align="center">
  <img src="docs/admin-fecha.png" alt="Gestión de Fechas" width="48%">
  <img src="docs/user-equipo.png" alt="Equipo Fantasy" width="48%">
</p>
