package it.flowing.workshop.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String elementId) {
        super(elementId + " not found");
    }
}
