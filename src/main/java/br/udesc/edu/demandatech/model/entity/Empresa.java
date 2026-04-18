package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "nome_social")
    private String nomeSocial;

    @Column(name = "flag_interno")
    private Boolean flagInterno;

    private String cnpj;

}
