package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Departamento;
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
            d.criador = ?2
    """)
    Optional<Demanda> getDemandasByIdAndUsuario(Long id, Funcionario criador);

    @Query(value = """
        SELECT f.* FROM Funcionario f 
        LEFT JOIN Demanda d ON f.responsavel = d.responsavel 
        WHERE f.id_departamento = :idDepartamento 
        GROUP BY f.responsavel 
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
        JOIN TipoDemanda td ON dem.tipo.idTipo = td.idTipo
        JOIN Departamento dp ON td.departamento.idDepartamento = dp.idDepartamento
        JOIN Status st ON dem.status = st.idStatus
        WHERE st.descricao = 'Ativo'
        GROUP BY dp.idDepartamento, dp.descricao
    """)
    List<QtdDemandasPorDepartamento> relatorioQtdDemandasPorDepartamento();

}
