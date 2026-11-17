# Sistema de Gestión de Biblioteca Universitaria

Proyecto final de Programación Orientada a Objetos (Java). Funciona por consola con un menú interactivo.

## Cómo compilar y ejecutar

Desde la carpeta `SistemaBiblioteca`:

```
javac -encoding UTF-8 -d bin src/com/biblioteca/*/*.java
java -cp bin com.biblioteca.app.App
```

## Estructura

- `com.biblioteca.modelo`: Personal, Bibliotecario, Auxiliar, Recurso, Libro, RecursoDigital, Prestamo, Seccion, Biblioteca, Validador
- `com.biblioteca.excepciones`: las cuatro excepciones personalizadas
- `com.biblioteca.app`: App (menú principal)

## Decisión de diseño (búsqueda de recursos)

`Biblioteca.buscarRecurso(codigo)` solo busca dentro de las secciones. Por eso, **un recurso debe ubicarse primero en una sección (opción 3)** antes de poder prestarlo (opción 4) o buscarlo por código (opción 8). Se eligió así porque un recurso que no está en ninguna sección todavía no está disponible físicamente en la biblioteca.

## Conceptos de POO aplicados

- **Abstracción y herencia:** `Personal` y `Recurso` son abstractas.
- **final:** `RecursoDigital` no se puede heredar.
- **Encapsulamiento:** todos los atributos son `private`.
- **Polimorfismo estático (sobrecarga):** `registrarPrestamo` en `Bibliotecario`.
- **Polimorfismo dinámico (sobreescritura):** `generarReporte()` y `obtenerInfo()`.
- **Composición:** `Biblioteca` crea sus `Seccion`.
- **Agregación:** `Seccion` guarda `Recurso` en un `ArrayList`.
- **Asociación:** `Bibliotecario` → `Prestamo` → `Recurso`.
- **Excepciones personalizadas** y validaciones con `Validador`.

## Diagrama de clases

Archivo `diagrama-biblioteca.puml` (PlantUML).
