package com.biblioteca.modelo;

import java.util.ArrayList;

import com.biblioteca.excepciones.PersonalNoEncontradoException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;

// Composición con Seccion: la Biblioteca crea sus propias secciones
public class Biblioteca {

    private String nombre;
    private ArrayList<Seccion> secciones;
    private ArrayList<Personal> personal;

    public Biblioteca(String nombre) {
        this.nombre = nombre;
        secciones = new ArrayList<Seccion>();
        personal = new ArrayList<Personal>();

        // Secciones iniciales creadas dentro de la propia biblioteca
        secciones.add(new Seccion("Referencia", 10));
        secciones.add(new Seccion("Literatura", 8));
        secciones.add(new Seccion("Ciencias", 6));
    }

    public void agregarSeccion(String nombre, int capacidad) {
        // La Biblioteca crea el objeto Seccion (esto mantiene la composición)
        Seccion nueva = new Seccion(nombre, capacidad);
        secciones.add(nueva);
        System.out.println("Sección '" + nombre + "' creada con capacidad " + capacidad);
    }

    public void agregarPersonal(Personal p) {
        personal.add(p);
        System.out.println(p.getNombre() + " registrado en la biblioteca.");
    }

    // Retorna la sección o null si no existe
    public Seccion buscarSeccion(String nombre) {
        for (int i = 0; i < secciones.size(); i++) {
            if (secciones.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return secciones.get(i);
            }
        }
        return null;
    }

    // Busca el recurso en todas las secciones
    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {
        for (int i = 0; i < secciones.size(); i++) {
            try {
                return secciones.get(i).buscarRecurso(codigo);
            } catch (RecursoNoEncontradoException e) {
                // No está en esta sección, seguimos con la siguiente
            }
        }
        throw new RecursoNoEncontradoException(codigo);
    }

    public Bibliotecario buscarBibliotecario(String id) throws PersonalNoEncontradoException {
        for (int i = 0; i < personal.size(); i++) {
            Personal p = personal.get(i);
            if (p instanceof Bibliotecario && p.getId().equals(id)) {
                return (Bibliotecario) p;
            }
        }
        throw new PersonalNoEncontradoException(id);
    }

    public void listarBibliotecarios() {
        System.out.println("--- Bibliotecarios ---");
        for (int i = 0; i < personal.size(); i++) {
            Personal p = personal.get(i);
            if (p instanceof Bibliotecario) {
                System.out.println(p.getId() + " - " + p.getNombre() + " (" + p.getArea() + ")");
            }
        }
    }

    public void generarReporteGeneral() {
        System.out.println("===== REPORTE GENERAL: " + nombre + " =====");
        for (int i = 0; i < secciones.size(); i++) {
            Seccion s = secciones.get(i);
            System.out.println("Sección: " + s.getNombre() + " | Ocupación: "
                    + s.getRecursos().size() + "/" + s.getCapacidad());
        }
        System.out.println("Total de personal: " + personal.size());
        for (int i = 0; i < personal.size(); i++) {
            // Polimorfismo dinámico: cada persona genera su propio reporte
            System.out.println(personal.get(i).generarReporte());
            System.out.println();
        }
    }

    public ArrayList<Seccion> getSecciones() {
        return secciones;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }
}
