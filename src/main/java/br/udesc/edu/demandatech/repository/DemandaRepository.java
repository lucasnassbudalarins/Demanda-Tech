package br.udesc.edu.demandatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.udesc.edu.demandatech.model.entity.Demanda;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Integer> {
}
