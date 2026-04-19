package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.UsuarioEnvolvido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEnvolvidoRepository extends JpaRepository<UsuarioEnvolvido, Long> {
}
