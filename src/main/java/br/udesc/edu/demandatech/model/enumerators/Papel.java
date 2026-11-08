package br.udesc.edu.demandatech.model.enumerators;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Papel {
    ADMIN("Administrador"),
    USER("Usuário");

    private final String descricao;
}

