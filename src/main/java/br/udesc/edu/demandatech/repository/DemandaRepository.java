package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {

    @Query("""
        SELECT d FROM Demanda d
        WHERE
            d.idDemanda = ?1 AND
            d.usuario = ?2
    """)
    Optional<Demanda> getDemandasByIdAndUsuario(Long id, Usuario usuario);

}
