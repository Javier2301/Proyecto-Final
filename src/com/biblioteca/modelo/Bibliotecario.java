package com.biblioteca.modelo;

import java.util.ArrayList;

import com.biblioteca.excepciones.PrestamoInvalidoException;

public class Bibliotecario extends Personal {

    private ArrayList<Prestamo> prestamos;

    public Bibliotecario(String nombre, String id, String area) {
        super(nombre, id, area);
        prestamos = new ArrayList<Prestamo>();
    }

    // Primera versión (SOBRECARGA): sin observación
    public void registrarPrestamo(String fecha, String lector, Recurso recurso)
            throws PrestamoInvalidoException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La fecha no puede estar vacía.");
        }
        if (lector == null || lector.trim().isEmpty()) {
            throw new PrestamoInvalidoException("Debe indicarse el nombre del lector.");
        }
        if (recurso == null) {
            throw new PrestamoInvalidoException("Debe indicarse un recurso válido.");
        }
        Prestamo p = new Prestamo(fecha, "Préstamo estándar", lector, recurso, this);
        prestamos.add(p);
        System.out.println("Préstamo registrado: " + recurso.getTitulo() + " a " + lector);
    }

    // Segunda versión (SOBRECARGA): con observación
    public void registrarPrestamo(String fecha, String lector, String observacion, Recurso recurso)
            throws PrestamoInvalidoException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La fecha no puede estar vacía.");
        }
        if (lector == null || lector.trim().isEmpty()) {
            throw new PrestamoInvalidoException("Debe indicarse el nombre del lector.");
        }
        if (recurso == null) {
            throw new PrestamoInvalidoException("Debe indicarse un recurso válido.");
        }
        if (observacion == null || observacion.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La observación no puede estar vacía.");
        }
        Prestamo p = new Prestamo(fecha, observacion, lector, recurso, this);
        prestamos.add(p);
        System.out.println("Préstamo registrado: " + recurso.getTitulo() + " a " + lector
                + " (" + observacion + ")");
    }

    // SOBREESCRITURA de generarReporte()
    @Override
    public String generarReporte() {
        return "=== BIBLIOTECARIO ===\nNombre: " + getNombre()
                + "\nID: " + getId()
                + "\nÁrea: " + getArea()
                + "\nPréstamos registrados: " + prestamos.size();
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
}
