package com.biblioteca.modelo;

// Clase abstracta: representa a cualquier persona que trabaja en la biblioteca
public abstract class Personal {

    private String nombre;
    private String id;
    private String area;

    public Personal(String nombre, String id, String area) {
        Validador.validarNombre(nombre);
        this.nombre = nombre;
        this.id = id;
        this.area = area;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    // Cada subclase decide cómo genera su reporte
    public abstract String generarReporte();
}
