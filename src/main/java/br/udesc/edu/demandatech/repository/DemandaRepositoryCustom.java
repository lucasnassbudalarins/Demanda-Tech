package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import java.util.List;
import java.util.Optional;

public interface DemandaRepositoryCustom {
    Optional<Funcionario> findFuncionarioComMenosDemandas(String idDepartamento);
    List<QtdDemandasPorDepartamento> relatorioQtdDemandasPorDepartamento();
    List<DezFuncionariosMaisProdutivos> relatorioDezFuncionariosMaisProdutivos();
}