package br.udesc.edu.demandatech.model.exception;

public class ItemNaoEncontrado extends RuntimeException {
    public ItemNaoEncontrado(String nomeNaoEncontrado) {

        super("O " + nomeNaoEncontrado + " nao foi encontrado");
    }
}
