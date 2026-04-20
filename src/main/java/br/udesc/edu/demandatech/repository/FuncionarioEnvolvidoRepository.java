package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvido;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioEnvolvidoRepository extends JpaRepository<FuncionarioEnvolvido, FuncionarioEnvolvidoId> {
    List<FuncionarioEnvolvido> findByDemanda(Demanda demanda);
}
