package br.udesc.edu.demandatech.model.exception;

public class IdNotFound extends RuntimeException {
    public IdNotFound(String nameClass, Long id) {
        super("O id " + id + " informado não foi encontrado em " + nameClass);
    }
}
