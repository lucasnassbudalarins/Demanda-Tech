package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel;
import br.udesc.edu.demandatech.model.entity.Funcionario;

import java.util.List;

public interface EstornoDemandaRepositoryCustom {
    List<QtdEstornosPorResponsavel> relatorioQtdEstornosPorResponsavel();
    boolean funcionarioCanEdit(String idEstorno, Funcionario funcionario);
}