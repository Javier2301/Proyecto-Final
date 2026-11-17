package com.biblioteca.excepciones;

// Se lanza cuando los datos de un préstamo no son válidos
public class PrestamoInvalidoException extends Exception {

    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
