package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstornoDemandaRepository extends JpaRepository<EstornoDemanda, Long> {
}
