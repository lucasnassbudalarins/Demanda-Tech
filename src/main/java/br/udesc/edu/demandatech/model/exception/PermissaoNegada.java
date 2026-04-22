package br.udesc.edu.demandatech.model.exception;

public class PermissaoNegada extends RuntimeException {
    public PermissaoNegada() {
        super("Usuário não tem permissão para realizar essa ação");
    }
}
