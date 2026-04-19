package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioEnvolvido {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_demanda")
    private Demanda idDemanda;

}
