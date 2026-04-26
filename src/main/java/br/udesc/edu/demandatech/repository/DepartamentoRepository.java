package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    Departamento getDepartamentoByDescricao(String descricao);
}
