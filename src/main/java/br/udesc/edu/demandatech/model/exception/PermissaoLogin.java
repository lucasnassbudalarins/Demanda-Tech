package br.udesc.edu.demandatech.model.exception;

public class PermissaoLogin extends RuntimeException {
    public PermissaoLogin() {
        super("Matrícula ou senha incorretos.");
    }
}
