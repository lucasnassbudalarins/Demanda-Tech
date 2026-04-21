package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
        JOIN dem.status st
        WHERE st.descricao = 'Ativo'
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
        JOIN dem.status st
        WHERE st.descricao = 'Resolvido'
        GROUP BY f.matricula, f.nome
        ORDER BY COUNT(dem) DESC
        LIMIT 10
    """)
    List<DezFuncionariosMaisProdutivos> relatorioDezFuncionariosMaisProdutivos();
}
