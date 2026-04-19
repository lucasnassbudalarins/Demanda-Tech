package br.udesc.edu.demandatech.model.exception;

public class SemFuncionarioDisponivel extends RuntimeException {
    public SemFuncionarioDisponivel() {
        super("Não foi possível criar a demanda pois não há nenhum funcionário disponível para atende-la");
    }
}
