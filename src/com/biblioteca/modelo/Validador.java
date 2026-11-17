package com.biblioteca.modelo;

// Clase de utilidad: solo tiene métodos estáticos y no se puede instanciar
public class Validador {

    // Constructor privado para que nadie cree objetos de esta clase
    private Validador() {
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        // Recorremos cada letra del nombre buscando dígitos
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isDigit(nombre.charAt(i))) {
                throw new IllegalArgumentException("El nombre no puede contener números: " + nombre);
            }
        }
    }

    public static void validarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty() || codigo.contains(" ")) {
            throw new IllegalArgumentException("Código inválido: no puede ser vacío ni tener espacios.");
        }
    }
}
