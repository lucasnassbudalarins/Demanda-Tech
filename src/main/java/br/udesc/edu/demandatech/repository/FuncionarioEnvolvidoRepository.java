package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioEnvolvidoRepository extends JpaRepository<FuncionarioEnvolvido, Long> {
}
