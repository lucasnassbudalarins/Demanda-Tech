package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstornoDemandaRepository extends JpaRepository<EstornoDemanda, Long> {

    List<EstornoDemanda> findByDemanda(Demanda demanda);
    @Query("""
    SELECT COUNT(ed) > 0
    FROM EstornoDemanda ed
    JOIN ed.demanda d
    WHERE
        ed.idEstorno = :idEstorno
        AND (d.criador = :funcionario OR d.responsavel = :funcionario)
    """)
    boolean funcionarioCanEdit(
            @Param("idEstorno") Long idEstorno,
            @Param("funcionario") Funcionario funcionario
    );

    @Query("""
        SELECT
            new br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel(
                dem.responsavel.matricula, dem.responsavel.nome, COUNT(ed)
            )
        FROM EstornoDemanda ed
        JOIN ed.demanda dem
        GROUP BY dem.responsavel.matricula, dem.responsavel.nome
        ORDER BY COUNT(ed) DESC
    """)
    List<QtdEstornosPorResponsavel> relatorioQtdEstornosPorResponsavel();
}