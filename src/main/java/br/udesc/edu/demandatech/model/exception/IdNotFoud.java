package br.udesc.edu.demandatech.model.exception;

public class IdNotFoud extends RuntimeException {
    public IdNotFoud(String nameClass, Long id) {
        super("O id " + id + " informado não foi encontrado em " + nameClass);
    }
}
