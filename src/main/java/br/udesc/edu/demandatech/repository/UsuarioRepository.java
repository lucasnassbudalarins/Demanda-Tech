package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
        SELECT u.admin FROM Usuario u
    """)
    boolean isAdmin(Usuario usuario);
}
