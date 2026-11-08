package br.udesc.edu.demandatech.model.enumerators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ABERTA("Aberta"),
    AGUARDANDO("Aguardando"),
    RESOLVIDA("Resolvida"),
    CANCELADA("Cancelada");

    private final String descricao;
}
