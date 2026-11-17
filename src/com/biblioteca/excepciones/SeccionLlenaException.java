package com.biblioteca.excepciones;

// Se lanza cuando una sección ya no tiene espacio para más recursos
public class SeccionLlenaException extends Exception {

    public SeccionLlenaException(String mensaje) {
        super(mensaje);
    }
}
