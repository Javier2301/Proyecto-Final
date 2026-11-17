package com.biblioteca.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.biblioteca.excepciones.PersonalNoEncontradoException;
import com.biblioteca.excepciones.PrestamoInvalidoException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;
import com.biblioteca.excepciones.SeccionLlenaException;
import com.biblioteca.modelo.Auxiliar;
import com.biblioteca.modelo.Biblioteca;
import com.biblioteca.modelo.Bibliotecario;
import com.biblioteca.modelo.Libro;
import com.biblioteca.modelo.Prestamo;
import com.biblioteca.modelo.Recurso;
import com.biblioteca.modelo.RecursoDigital;
import com.biblioteca.modelo.Seccion;

public class App {

    // Lee un entero y vuelve a preguntar mientras el usuario escriba algo que no lo sea
    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = sc.nextInt();
                sc.nextLine(); // limpia el salto de línea que queda en el buffer
                return numero;
            } catch (InputMismatchException e) {
                sc.nextLine(); // descarta lo que el usuario escribió mal
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("===== MENÚ BIBLIOTECA =====");
        System.out.println("1. Registrar recurso");
        System.out.println("2. Listar recursos registrados");
        System.out.println("3. Ubicar recurso en una sección");
        System.out.println("4. Registrar préstamo");
        System.out.println("5. Ver recursos de una sección");
        System.out.println("6. Ver préstamos de un bibliotecario");
        System.out.println("7. Dar de baja un recurso de una sección");
        System.out.println("8. Buscar recurso por código");
        System.out.println("9. Reporte general de la biblioteca");
        System.out.println("10. Crear nueva sección");
        System.out.println("0. Salir");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lista temporal con los recursos creados (aún sin ubicar en una sección)
        ArrayList<Recurso> recursosRegistrados = new ArrayList<Recurso>();

        Biblioteca biblioteca = new Biblioteca("Biblioteca Central Los Libertadores");

        Bibliotecario b1 = new Bibliotecario("Marta Rojas", "B001", "Circulación");
        Bibliotecario b2 = new Bibliotecario("Julián Pardo", "B002", "Referencia");
        Auxiliar a1 = new Auxiliar("Diana Cruz", "A001", "Catalogación", "Tarde");

        biblioteca.agregarPersonal(b1);
        biblioteca.agregarPersonal(b2);
        biblioteca.agregarPersonal(a1);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarRecurso(sc, recursosRegistrados);
                    break;
                case 2:
                    listarRecursosRegistrados(recursosRegistrados);
                    break;
                case 3:
                    ubicarRecurso(sc, biblioteca, recursosRegistrados);
                    break;
                case 4:
                    registrarPrestamo(sc, biblioteca);
                    break;
                case 5:
                    verRecursosSeccion(sc, biblioteca);
                    break;
                case 6:
                    verPrestamosBibliotecario(sc, biblioteca);
                    break;
                case 7:
                    darDeBaja(sc, biblioteca);
                    break;
                case 8:
                    buscarRecurso(sc, biblioteca);
                    break;
                case 9:
                    biblioteca.generarReporteGeneral();
                    break;
                case 10:
                    crearSeccion(sc, biblioteca);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Opción 1
    public static void registrarRecurso(Scanner sc, ArrayList<Recurso> recursosRegistrados) {
        int tipo = leerEntero(sc, "Tipo de recurso (1=Libro, 2=Digital): ");
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido");
            return;
        }

        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Código: ");
        String codigo = sc.nextLine();
        int anio = leerEntero(sc, "Año: ");

        try {
            if (tipo == 1) {
                System.out.print("Autor: ");
                String autor = sc.nextLine();
                int paginas = leerEntero(sc, "Páginas: ");
                Libro libro = new Libro(titulo, codigo, anio, autor, paginas);
                recursosRegistrados.add(libro);
            } else {
                System.out.print("Formato (PDF, EPUB, etc.): ");
                String formato = sc.nextLine();
                int tamano = leerEntero(sc, "Tamaño en MB: ");
                RecursoDigital digital = new RecursoDigital(titulo, codigo, anio, formato, tamano);
                recursosRegistrados.add(digital);
            }
            System.out.println("Recurso registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 2
    public static void listarRecursosRegistrados(ArrayList<Recurso> recursosRegistrados) {
        if (recursosRegistrados.isEmpty()) {
            System.out.println("No hay recursos registrados.");
            return;
        }
        for (int i = 0; i < recursosRegistrados.size(); i++) {
            System.out.println(recursosRegistrados.get(i).obtenerInfo());
        }
    }

    // Opción 3
    public static void ubicarRecurso(Scanner sc, Biblioteca biblioteca,
                                     ArrayList<Recurso> recursosRegistrados) {
        System.out.print("Nombre de la sección: ");
        String nombreSeccion = sc.nextLine();
        Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
        if (seccion == null) {
            System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
            return;
        }

        System.out.print("Código del recurso: ");
        String codigo = sc.nextLine();

        // Buscamos el recurso en la lista de registrados
        Recurso encontrado = null;
        for (int i = 0; i < recursosRegistrados.size(); i++) {
            if (recursosRegistrados.get(i).getCodigo().equals(codigo)) {
                encontrado = recursosRegistrados.get(i);
            }
        }
        if (encontrado == null) {
            System.out.println("Error: el recurso con código " + codigo + " no está registrado.");
            return;
        }

        try {
            seccion.agregarRecurso(encontrado);
        } catch (SeccionLlenaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 4
    public static void registrarPrestamo(Scanner sc, Biblioteca biblioteca) {
        biblioteca.listarBibliotecarios();
        System.out.print("Id del bibliotecario: ");
        String id = sc.nextLine();

        try {
            Bibliotecario bibliotecario = biblioteca.buscarBibliotecario(id);

            System.out.print("Código del recurso: ");
            String codigo = sc.nextLine();
            Recurso recurso = biblioteca.buscarRecurso(codigo);

            System.out.print("Fecha (dd/mm/aaaa): ");
            String fecha = sc.nextLine();
            System.out.print("Nombre del lector: ");
            String lector = sc.nextLine();
            System.out.print("¿Agrega observación? (s/n): ");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("Observación: ");
                String observacion = sc.nextLine();
                bibliotecario.registrarPrestamo(fecha, lector, observacion, recurso);
            } else {
                bibliotecario.registrarPrestamo(fecha, lector, recurso);
            }
        } catch (PersonalNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RecursoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PrestamoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 5
    public static void verRecursosSeccion(Scanner sc, Biblioteca biblioteca) {
        System.out.print("Nombre de la sección: ");
        String nombreSeccion = sc.nextLine();
        Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
        if (seccion == null) {
            System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
        } else {
            seccion.listarRecursos();
        }
    }

    // Opción 6
    public static void verPrestamosBibliotecario(Scanner sc, Biblioteca biblioteca) {
        biblioteca.listarBibliotecarios();
        System.out.print("Id del bibliotecario: ");
        String id = sc.nextLine();
        try {
            Bibliotecario b = biblioteca.buscarBibliotecario(id);
            ArrayList<Prestamo> lista = b.getPrestamos();
            if (lista.isEmpty()) {
                System.out.println("Este bibliotecario no tiene préstamos registrados.");
            } else {
                for (int i = 0; i < lista.size(); i++) {
                    System.out.println(lista.get(i).getInfo());
                }
            }
        } catch (PersonalNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 7
    public static void darDeBaja(Scanner sc, Biblioteca biblioteca) {
        System.out.print("Nombre de la sección: ");
        String nombreSeccion = sc.nextLine();
        Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
        if (seccion == null) {
            System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
            return;
        }
        System.out.print("Código del recurso: ");
        String codigo = sc.nextLine();
        try {
            seccion.retirarRecurso(codigo);
        } catch (RecursoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 8
    public static void buscarRecurso(Scanner sc, Biblioteca biblioteca) {
        System.out.print("Código del recurso: ");
        String codigo = sc.nextLine();
        try {
            Recurso r = biblioteca.buscarRecurso(codigo);
            System.out.println(r.obtenerInfo());
        } catch (RecursoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opción 10
    public static void crearSeccion(Scanner sc, Biblioteca biblioteca) {
        System.out.print("Nombre de la nueva sección: ");
        String nombre = sc.nextLine();
        int capacidad = leerEntero(sc, "Capacidad: ");
        try {
            biblioteca.agregarSeccion(nombre, capacidad);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
