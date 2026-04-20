package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.PrioridadeCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.PrioridadeEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.repository.PrioridadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrioridadeService {
    private PrioridadeRepository prioridadeRepository;

    public Prioridade criar(PrioridadeCriarDTO prioridadeCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Prioridade prioridade = new Prioridade();
        BeanUtils.copyProperties(prioridadeCriarDTO, prioridade);

        return prioridadeRepository.save(prioridade);
    }

    public Prioridade atualizar(Long id, PrioridadeEditarDTO prioridadeEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Prioridade> opcionalPrioridade = prioridadeRepository.findById(id);
        if(opcionalPrioridade.isPresent()) {
            Prioridade prioridade = opcionalPrioridade.get();
            BeanUtils.copyProperties(prioridadeEditarDTO, prioridade);
            return prioridadeRepository.save(prioridade);
        }
        throw new IdNaoEncontrado("prioridade", id);
    }

    public List<Prioridade> buscarTudo() {
        return prioridadeRepository.findAll();
    }

    public Prioridade buscarPorId(Long id) {
        Optional<Prioridade> opcionalPrioridade = prioridadeRepository.findById(id);
        if(opcionalPrioridade.isPresent()) {
            return opcionalPrioridade.get();
        }
        throw new IdNaoEncontrado("prioridade", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Prioridade> optionalPrioridade = prioridadeRepository.findById(id);
        if(optionalPrioridade.isPresent()) {
            prioridadeRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("prioridade", id);
    }
}
