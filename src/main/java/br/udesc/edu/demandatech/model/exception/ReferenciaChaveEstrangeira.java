package br.udesc.edu.demandatech.model.exception;

public class ReferenciaChaveEstrangeira extends RuntimeException {
    public ReferenciaChaveEstrangeira() {
        super("Não é possível remover ou atualizar um registro que está sendo referenciado por outro.");
    }
}