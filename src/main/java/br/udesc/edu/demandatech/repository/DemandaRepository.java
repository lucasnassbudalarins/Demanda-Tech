package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.enumerators.Prioridade;
import br.udesc.edu.demandatech.model.enumerators.Status;
import br.udesc.edu.demandatech.model.projection.DemandaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {

    @Query("""
        SELECT d FROM Demanda d
        WHERE
            d.idDemanda = ?1 AND
            (d.criador = ?2 OR d.responsavel = ?2)
    """)
    Optional<Demanda> getDemandasByIdAndUsuario(Long id, Funcionario funcionario);

    @Query(value = """
        SELECT f.* FROM demanda_tech.funcionarios f
        LEFT JOIN demanda_tech.demandas d ON d.responsavel = f.matricula
        WHERE f.id_departamento = :idDepartamento
        GROUP BY f.matricula
        ORDER BY COUNT(d.id_demanda) ASC
        LIMIT 1
    """, nativeQuery = true)
    Optional<Funcionario> findFuncionarioComMenosDemandas(@Param("idDepartamento") Long idDepartamento);

    @Query("""
        SELECT
            new br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento(
                dp.idDepartamento, dp.descricao, COUNT(dem)
            )
        FROM Demanda dem
        JOIN dem.tipo td
        JOIN td.departamento dp
        WHERE dem.status = br.udesc.edu.demandatech.model.enumerators.Status.ABERTA
        GROUP BY dp.idDepartamento, dp.descricao
    """)
    List<QtdDemandasPorDepartamento> relatorioQtdDemandasPorDepartamento();

    @Query("""
        SELECT
            new br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos(
                f.matricula, f.nome, COUNT(dem)
            )
        FROM Demanda dem
        JOIN dem.responsavel f
        WHERE dem.status = br.udesc.edu.demandatech.model.enumerators.Status.RESOLVIDA
        GROUP BY f.matricula, f.nome
        ORDER BY COUNT(dem) DESC
        LIMIT 10
    """)
    List<DezFuncionariosMaisProdutivos> relatorioDezFuncionariosMaisProdutivos();

@Query(value = """
        SELECT
            new br.udesc.edu.demandatech.model.projection.DemandaProjection(
                d.idDemanda, d.titulo, d.status, d.prioridade, t.descricao, r.nome, d.data
            )
        FROM Demanda d
        JOIN d.tipo t
        LEFT JOIN d.responsavel r
        WHERE (:status IS NULL OR d.status = :status)
          AND (:prioridade IS NULL OR d.prioridade = :prioridade)
          AND (:idTipo IS NULL OR t.idTipo = :idTipo)
    """,
    countQuery = """
        SELECT COUNT(d)
        FROM Demanda d
        JOIN d.tipo t
        WHERE (:status IS NULL OR d.status = :status)
          AND (:prioridade IS NULL OR d.prioridade = :prioridade)
          AND (:idTipo IS NULL OR t.idTipo = :idTipo)
    """)
Page<DemandaProjection> listarDemandas(
    @Param("status") Status status,
    @Param("prioridade") Prioridade prioridade,
    @Param("idTipo") Long idTipo,
    Pageable pageable
);
}
