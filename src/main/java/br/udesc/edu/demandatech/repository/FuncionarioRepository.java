package br.udesc.edu.demandatech.repository;


import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByMatriculaAndSenha(Long matricula, String senha);
}


