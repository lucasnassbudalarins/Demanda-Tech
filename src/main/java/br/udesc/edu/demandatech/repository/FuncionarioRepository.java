package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("""
        SELECT f FROM Funcionario f
        WHERE
            f.usuario.empresa = ?1 OR
            d.usuario.empresa.flagInterno = true
    """)
    List<Demanda> getDemandasByEmpresa(Empresa empresa);

    @Query("""
        SELECT f FROM Funcionario f
        WHERE
            d.idDemanda = ?1 AND
            (d.usuario.empresa = ?2 OR
            d.usuario.empresa.flagInterno = true)
    """)
    Optional<Demanda> getDemandaByEmpresa(Long id, Empresa empresa);

    @Query("""
        SELECT d FROM Demanda d
        WHERE
            d.idDemanda = ?1 AND
            d.usuario = ?2
    """)
    Optional<Demanda> getDemandasByIdAndUsuario(Long id, Usuario usuario);
}
