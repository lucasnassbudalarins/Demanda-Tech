package br.udesc.edu.demandatech.model.exception;

public class PermissionDenied extends RuntimeException {
    public PermissionDenied() {
        super("Usuário não tem permissão para realizar essa ação");
    }
}
