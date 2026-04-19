package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
