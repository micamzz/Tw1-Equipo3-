# 🏀 Basket UNLAM

Aplicación web desarrollada como **Trabajo Práctico Grupal** para la materia **Taller Web I** de la **Universidad
Nacional de La Matanza (UNLaM)**.

El proyecto simula una plataforma de **Fantasy NBA**, donde cada usuario puede crear y administrar su propio equipo,
respetando un presupuesto determinado, seleccionando jugadores reales de la NBA y compitiendo en un torneo mediante un
sistema de puntajes basado en estadísticas reales.

El objetivo principal fue aplicar los conocimientos adquiridos durante la cursada utilizando **Java**, **Spring MVC**,
**Hibernate**, **JPA**, **Maven** y buenas prácticas de desarrollo de software.

## Características

|                                              |
|----------------------------------------------|
| Arquitectura MVC                             |
| Arquitectura en capas                        |
| Persistencia con Hibernate/JPA               |
| Gestión de usuarios                          |
| Creación y administración de equipos Fantasy |
| Sistema de presupuesto                       |
| Gestión de jugadores                         |
| Asignación de Capitán y Sexto Hombre         |
| Sistema automático de puntajes               |
| Testing con JUnit y Mockito                  |

# Objetivos del proyecto

- Aplicar el patrón MVC.
- Implementar una arquitectura en capas.
- Utilizar Hibernate para la persistencia de datos.
- Gestionar relaciones entre entidades mediante JPA.
- Implementar reglas de negocio desacopladas.
- Trabajar colaborativamente utilizando Git y GitHub.
- Realizar pruebas unitarias utilizando JUnit y Mockito.

# Funcionalidades

## 👨‍💼 Administrador

El administrador dispone de herramientas para gestionar completamente la plataforma.

Puede:

- Iniciar sesión como administrador.
- Crear equipos oficiales de la NBA.
- Modificar equipos NBA.
- Asignar jugadores a cada equipo NBA.
- Administrar la plantilla de jugadores.
- Crear nuevas temporadas.
- Crear torneos.
- Asociar equipos al torneo correspondiente.
- Administrar la información general del sistema.
- Asignar nuevos administradores.

## 👤 Usuarios

- Registrarse e iniciar sesión.
- Crear su propio equipo Fantasy.
- Seleccionar jugadores para conformar su plantilla.
- Administrar el presupuesto disponible.
- Incorporar y eliminar jugadores del equipo.
- Asignar los roles de **Capitán** y **Sexto Hombre**.
- Visualizar las estadísticas individuales de los jugadores.
- Consultar el valor de mercado de cada jugador.
- Ver el puntaje acumulado de su equipo.
- Consultar la información completa de su plantilla.
- Participar automáticamente del torneo vigente.
- Visualizar la tabla de posiciones.
- Modificar su equipo respetando las reglas de negocio.

## Roles Especiales

Cada equipo puede asignar dos roles especiales:

- Capitán
- Sexto Hombre

Estos modifican el puntaje obtenido por cada jugador durante la competencia.

## Torneos

- Asociación automática al torneo vigente.
- Administración de equipos participantes.
- Cálculo automático de puntajes.

# Sistema de Puntajes

El puntaje de cada jugador se calcula utilizando estadísticas reales como:

- Puntos
- Rebotes
- Asistencias
- Robos
- Bloqueos
- Pérdidas

Posteriormente se aplica un multiplicador dependiendo del rol asignado.

| Rol          | Multiplicador |
|--------------|:-------------:|
| Capitán      |    **x2**     |
| Titular      |    **x1**     |
| Sexto Hombre |   **x0.8**    |
| Suplente     |   **x0.5**    |

# Sistema de Presupuesto

Cada equipo dispone de un presupuesto inicial.

Cada jugador posee un valor de mercado.

Durante la creación del equipo:

- El presupuesto disminuye automáticamente.
- No es posible superar el presupuesto disponible.
- Al eliminar un jugador se reintegra automáticamente su valor.
- Todas las validaciones se realizan desde la capa de negocio.

# Arquitectura MVC

El proyecto fue desarrollado siguiendo una arquitectura en capas, separando claramente las responsabilidades de cada
componente.

## Estructura del proyecto

```text
src
│
├── main
│   ├── java
│   │   └── com.tallerwebi
│   │       ├── config
│   │       ├── dominio
│   │       ├── infraestructura
│   │       ├── presentacion
│   │       └── MyServletInitializer
│   │
│   ├── resources
│   │
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

## Responsabilidad de cada paquete

### 📂 config

Contiene la configuración general de la aplicación.

- Spring MVC
- Hibernate
- Beans
- Contexto de la aplicación

### 📂 dominio

Implementa toda la lógica de negocio del sistema.

Aquí se encuentran:

- Entidades
- Servicios
- Interfaces de repositorio
- Reglas de negocio

---

### 📂 infraestructura :

Implementaciones encargadas del acceso a datos utilizando Hibernate y JPA.

### 📂 presentacion

Controladores MVC responsables de recibir las solicitudes HTTP y comunicarse con la capa de servicios.

### 📂 webapp

Recursos de la aplicación.

- JSP
- CSS
- JavaScript
- Bootstrap
- Imágenes

# Flujo de una petición

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

Cada capa posee una única responsabilidad, favoreciendo un código mantenible, reutilizable y desacoplado.

# Testing

El proyecto cuenta con diferentes niveles de pruebas automatizadas.

```text
test
│
├── dominio
│      Pruebas unitarias de reglas de negocio
│
├── infraestructura
│      Persistencia y repositorios
│
├── presentacion
│      Controladores MVC
│
├── integracion
      Integración entre capas

```

Tecnologías utilizadas:

- JUnit 5
- Mockito

# Tecnologías utilizadas

| Backend    | Frontend   | Persistencia | Testing | Herramientas  |
|------------|------------|--------------|---------|---------------|
| Java 17    | HTML5      | Hibernate    | JUnit 5 | Maven         |
| Spring MVC | CSS3       | JPA          | Mockito | Git           |
| Spring     | JavaScript | MySQL        |         | GitHub        |
|            | Bootstrap  |              |         | IntelliJ IDEA |

# ▶ Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/usuario/nba-fantasy-manager.git
```

### 2. Ingresar al proyecto

```bash
cd nba-fantasy-manager
```

### 3. Compilar

```bash
mvn clean install
```

### 4. Ejecutar

```bash
mvn tomcat7:run
```

# Capturas

### Login

### Home

### Crear Equipo

### Administración de Jugadores

# Equipo de desarrollo

Trabajo Práctico Grupal desarrollado para la materia **Taller Web I** de la **Universidad Nacional de La Matanza (UNLaM)
**.



