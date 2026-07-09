package br.com.automated_tests_with_java;

public class ResourceNotFoundException  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String ex) {
        super(ex);
    }
}
