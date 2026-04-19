package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DemandaEditarDTO;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Usuario;
import br.udesc.edu.demandatech.model.entity.UsuarioEnvolvido;
import br.udesc.edu.demandatech.model.exception.IdNotFound;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import br.udesc.edu.demandatech.repository.UsuarioEnvolvidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;
    private UsuarioEnvolvidoRepository usuarioEnvolvidoRepository;

    public Demanda criar(DemandaCriarDTO demandaCriarDTO){
        Demanda demanda = new Demanda();
        BeanUtils.copyProperties(demandaCriarDTO,demanda);

//        Adicionar Funcionário (regra de negócio)

        demanda = demandaRepository.save(demanda);

        for (Usuario usuario : demandaCriarDTO.usuariosEnvolvidos())
            new UsuarioEnvolvido(usuario, demanda);

        return demanda;
    }

    public Demanda atualizar(Long id, DemandaEditarDTO demandaEditarDTO){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, demandaEditarDTO.usuario());
        if(optionalDemanda.isPresent()){
            Demanda demanda = optionalDemanda.get();
            BeanUtils.copyProperties(demandaEditarDTO,demanda);
            return demandaRepository.save(demanda);
        }
        throw new IdNotFound("demandas", id);
    }

    public List<Demanda> buscarTudo(){
        return demandaRepository.findAll();
    }

    public Demanda buscarPorId(Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if(optionalDemanda.isPresent()) {
            return optionalDemanda.get();
        }
        throw new IdNotFound("demandas", id);
    }

    public void removerPorId(Usuario usuario, Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, usuario);
        if(optionalDemanda.isPresent()) {
            demandaRepository.deleteById(id);
        }
        throw new IdNotFound("demandas", id);
    }

}
