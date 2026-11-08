package br.udesc.edu.demandatech.repository;


import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.projection.ColaboradorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByMatriculaAndSenha(Long matricula, String senha);

    @Query(value = """
            SELECT
                new br.udesc.edu.demandatech.model.projection.ColaboradorProjection(
                    f.nome, f.matricula;
                )
            FROM Funcionario f
        """,
            countQuery = """
            SELECT COUNT(f)
            FROM Funcionario f
        """)
    Page<ColaboradorProjection> listarFuncionarios(
        );

}


