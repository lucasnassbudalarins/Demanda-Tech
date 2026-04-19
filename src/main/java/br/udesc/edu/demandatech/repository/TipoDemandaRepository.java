package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDemandaRepository extends JpaRepository<TipoDemanda, Long> {
}
