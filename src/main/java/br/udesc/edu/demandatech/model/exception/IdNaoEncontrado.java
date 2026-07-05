package br.udesc.edu.demandatech.model.exception;

public class IdNaoEncontrado extends RuntimeException {
    public IdNaoEncontrado(String nameClass, String id) {
        super("O id " + id + " informado não foi encontrado em " + nameClass);
    }
}
