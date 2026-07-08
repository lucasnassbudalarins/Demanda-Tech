package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {
    Optional<Funcionario> findByMatriculaAndSenha(String matricula, String senha);
}
